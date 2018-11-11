package hu.tutor.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ValidationException;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.Binder;
import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ErrorLevel;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.model.Subject;
import hu.tutor.model.User;
import hu.tutor.model.search.SearchQuery;
import hu.tutor.model.search.SearchResult;
import hu.tutor.security.AuthService;
import hu.tutor.service.GoogleMapService;
import hu.tutor.service.SearchService;
import hu.tutor.service.SubjectService;
import hu.tutor.util.VaadinUtil;

@SpringView(name = SearchView.SEARCH_VIEW_NAME)
@Scope("prototype")
@SpringComponent
public class SearchView extends VerticalLayout implements View {

	@Value("${google.maps.api-key}")
	private String API_KEY;
	private static final String MAX_DISTANCE = "Maximum távolság (km)";
	private static final String TEACHER_NAME = "Tanár neve";
	private static final String SUBJECT_NAME = "Tantárgy";
	private static final String FILTERS = "Szűrők:";

	private static final long serialVersionUID = 9132068016374956428L;

	public static final String SEARCH_VIEW_NAME = "search";

	private User user;
	private Grid<SearchResult> resultGrid;
	private Panel searchPanel;
	private ComboBox<Subject> subjectComboBox;
	private TextField teacherNameField;
	private TextField distanceField;
	private Label filterLabel;
	private boolean authorizedAccess;
	private SearchResult detailsResultItem;
	private Button submitButton;
	private Button saveSearchButton;
	private List<Subject> allSubjects;
	private SearchQuery query;

	private Binder<SearchQuery> queryBinder;

	@Autowired
	private SearchService searchService;
	@Autowired
	private AuthService authService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private GoogleMapService googleMapService;

	@Override
	public void enter(ViewChangeEvent event) {
		this.user = (User) VaadinSession.getCurrent().getAttribute("user");
		this.query = (SearchQuery) VaadinSession.getCurrent().getAttribute(VaadinUtil.VAADIN_SESSION_QUERY_NAME);

		this.authorizedAccess = this.isAuthorizedAccess();

		HorizontalLayout linkLayout = new HorizontalLayout(this.createMainPageLink());
		if (this.authorizedAccess) {
			linkLayout.addComponent(this.createAccountLink());
			linkLayout.addComponent(this.createLogoutLink());
		}

		this.addComponents(linkLayout, this.createContent());
		this.createBinder();
		this.executeQuery();
	}

	private void executeQuery() {
		if (this.query != null) {
			this.submitButton.click();
			VaadinSession.getCurrent().setAttribute(VaadinUtil.VAADIN_SESSION_QUERY_NAME, null);
		}
	}

	private Component createMainPageLink() {
		return new Link("Főoldal", new ExternalResource("#!" + MainView.MAIN_VIEW_NAME));
	}

	private Component createAccountLink() {
		return new Link("Fiók", new ExternalResource("#!" + AccountView.ACCOUNT_VIEW_NAME));
	}

	private Component createLogoutLink() {
		return new Link("Kijelentkezés", new ExternalResource("#!" + LogoutView.LOGOUT_VIEW_NAME));
	}

	private Component createContent() {
		return new VerticalLayout(this.createSearchLayout(), this.createResultLayout());
	}

	private Component createSearchLayout() {
		Panel panel = new Panel("Keresés");
		VerticalLayout layout = new VerticalLayout();

		this.subjectComboBox = new ComboBox<>(SUBJECT_NAME);
		this.teacherNameField = new TextField(TEACHER_NAME);
		this.distanceField = new TextField(MAX_DISTANCE);
		this.submitButton = new Button("Keresés");

		this.allSubjects = this.subjectService.getAllSubjects();
		this.subjectComboBox.setItems(this.allSubjects);
		this.subjectComboBox.setItemCaptionGenerator(Subject::getName);

		this.subjectComboBox.setWidth("100%");
		this.teacherNameField.setWidth("80%");
		this.distanceField.setWidth("80%");

		this.submitButton.addClickListener(e -> {
			SearchQuery bean = this.queryBinder.getBean();
			if (Stream
					.of(bean.getSubjectName(), bean.getTeacherName(),
							bean.getMaxDistance() != null ? bean.getMaxDistance().toString() : null)
					.filter(s -> s != null && !s.isEmpty()).count() > 0) {
				if (this.queryBinder.isValid()) {
					Integer userId = this.user != null ? this.user.getId() : null;

					try {
						this.filterLabel.setCaption(this.rewriteFilterLabel());

						List<SearchResult> results = this.searchService.doSearch(userId, this.queryBinder.getBean());

						if (results == null) {
							results = new ArrayList<>();
						}

						this.checkDistances(results);
						this.saveSearchButton.setEnabled(true);

						this.resultGrid.setItems(results);

					} catch (ValidationException ex) {
						Notification.show("Nem szám", Type.ERROR_MESSAGE);
					}
				} else {
					Notification.show("Kérem ellenőrizze a megadott feltételeket", Type.ERROR_MESSAGE);
				}
			} else {
				Notification.show("Legalább egy feltétel megadása kötelező", Type.WARNING_MESSAGE);
			}
		});

		HorizontalLayout horizontal = new HorizontalLayout(this.subjectComboBox, this.teacherNameField);
		if (this.authorizedAccess) {
			horizontal.addComponent(this.distanceField);
		}
		horizontal.setSizeFull();

		layout.addComponents(horizontal, this.submitButton);
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();

		panel.setSizeFull();

		panel.setContent(layout);

		return panel;
	}

	private void checkDistances(List<SearchResult> results) {
		if (this.authorizedAccess) {
			results.stream().filter(r -> r.getDistance() < 0).collect(Collectors.toList()).forEach(r -> {
				Integer distance = this.googleMapService.getDistanceBetweenCities(this.user.getAddress().getCity(),
						r.getCity());

				r.setDistance(distance);

				this.searchService.saveResultDistance(this.user.getAddress().getCity(), r.getCity(), distance);
			});

			Integer maxDistance = this.queryBinder.getBean().getMaxDistance();
			if (maxDistance != null) {
				results = results.stream().filter(r -> maxDistance.compareTo(r.getDistance()) > 0)
						.collect(Collectors.toList());
			}
		}
	}

	private String rewriteFilterLabel() {
		StringBuilder builder = new StringBuilder();
		builder.append(FILTERS);
		boolean filterPresent = false;

		SearchQuery bean = this.queryBinder.getBean();
		String subjectName = bean.getSubjectName();
		String teacherName = bean.getTeacherName();
		Integer maxDistance = bean.getMaxDistance();

		if (subjectName != null) {
			if (filterPresent) {
				builder.append(",");
			}
			builder.append(" ").append(SUBJECT_NAME).append(" = ").append(subjectName);
			filterPresent = true;
		}

		if (teacherName != null) {
			if (filterPresent) {
				builder.append(",");
			}
			builder.append(" ").append(TEACHER_NAME).append(" = ").append(teacherName);
			filterPresent = true;
		}

		if (maxDistance != null) {
			if (filterPresent) {
				builder.append(",");
			}
			builder.append(" ").append("Maximum távolság").append(" = ").append(maxDistance / 1000).append(" km");
			filterPresent = true;
		}

		if (filterPresent) {
			return builder.toString();
		} else {
			return "";
		}
	}

	private Component createResultLayout() {
		this.searchPanel = new Panel("Találatok");
		VerticalLayout layout = new VerticalLayout();
		this.filterLabel = new Label("");

		this.resultGrid = new Grid<>();

		this.resultGrid.addColumn(SearchResult::getFullName).setDescriptionGenerator(SearchResult::getFullName)
				.setCaption("Név");
		this.resultGrid.addColumn(SearchResult::getIntroduction).setDescriptionGenerator(SearchResult::getIntroduction)
				.setCaption("Bemutatkozás");
		this.resultGrid.addColumn(SearchResult::getPersonalSubjectDescription)
				.setDescriptionGenerator(SearchResult::getPersonalSubjectDescription)
				.setCaption("Személyes tantárgy leírás");

		this.resultGrid.setSelectionMode(SelectionMode.NONE);

		if (this.authorizedAccess) {
			this.resultGrid.addColumn(SearchResult::getCity).setDescriptionGenerator(SearchResult::getCity)
					.setCaption("Város");
			this.resultGrid.addColumn(SearchResult::getStreet).setDescriptionGenerator(SearchResult::getStreet)
					.setCaption("Utca");
			this.resultGrid.addColumn(SearchResult::getHouseNumber)
					.setDescriptionGenerator(SearchResult::getHouseNumber).setCaption("Házszám");
			this.resultGrid.addColumn(SearchResult::getFormattedPhoneNumber)
					.setDescriptionGenerator(SearchResult::getFormattedPhoneNumber).setCaption("Telefonszám");
			this.resultGrid.addColumn(SearchResult::getEmail).setDescriptionGenerator(SearchResult::getEmail)
					.setCaption("Email");
			this.resultGrid.addColumn(r -> r.getDistance() / 1000 + " km")
					.setDescriptionGenerator(r -> r.getDistance() / 1000 + " km").setCaption("Távolság");

			this.resultGrid.setDetailsGenerator(this::getResultGridDetailsGenerator);
		}

		this.resultGrid.addItemClickListener(this::getResultGridClickListener);

		this.resultGrid.setSizeFull();
		layout.addComponents(this.filterLabel, this.createSaveSearchButton(), this.resultGrid);
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();

		this.searchPanel.setSizeFull();
		this.searchPanel.setContent(layout);

		return this.searchPanel;
	}

	private Component createSaveSearchButton() {

		this.saveSearchButton = new Button("Keresés mentése");
		this.saveSearchButton.setEnabled(false);

		this.saveSearchButton.addClickListener(e -> {
			SearchQuery query = this.queryBinder.getBean();
			query.setOwner(this.user);

			this.searchService.saveSearchQuery(query);
		});
		if (!this.authorizedAccess) {
			this.saveSearchButton.setVisible(false);
		}
		return this.saveSearchButton;

	}

	private Component getResultGridDetailsGenerator(SearchResult result) {
		HorizontalLayout horizontal = new HorizontalLayout();

		GoogleMap map = new GoogleMap(this.API_KEY, null, "hungarian");

		LatLon resultGeoCode = this.googleMapService.getGeoCode(result.getZip(), result.getCountry(), result.getCity(),
				result.getStreet(), result.getHouseNumber());

		LatLon userGeoCode = this.googleMapService.getGeoCode(this.user.getAddress());

		map.addMarker(new GoogleMapMarker(result.getFullName(), resultGeoCode, false));
		map.addMarker(new GoogleMapMarker(this.user.getFullName(), userGeoCode, false));

		LatLon[] bounds = this.googleMapService
				.getBounds(map.getMarkers().stream().map(GoogleMapMarker::getPosition).collect(Collectors.toList()));

		map.fitToBounds(bounds[0], bounds[1]);

		map.setSizeFull();

		horizontal.addComponent(map);
		horizontal.setSizeFull();

		return horizontal;
	}

	private void getResultGridClickListener(ItemClick<SearchResult> result) {
		Grid<SearchResult> sourceGrid = result.getSource();
		SearchResult resultItem = result.getItem();

		if (result.getMouseEventDetails().isDoubleClick()) {
			if (this.detailsResultItem != null) {
				sourceGrid.setDetailsVisible(this.detailsResultItem, false);
			}

			if (this.authorizedAccess) {
				this.detailsResultItem = resultItem;
				sourceGrid.setDetailsVisible(resultItem, true);
			} else {
				Notification.show("Hozzáférés megtagadva",
						"Nem bejelentkezett felhasználó nem láthatja a részleteket.\nKérjük jelentkezzen be, vagy regisztráljon.",
						Type.ERROR_MESSAGE);
			}
		} else {
			if (sourceGrid.isDetailsVisible(resultItem)) {
				sourceGrid.setDetailsVisible(resultItem, false);
				this.detailsResultItem = null;
			}
		}
	}

	private void createBinder() {
		this.queryBinder = new Binder<>();

		this.queryBinder.forField(this.subjectComboBox).withConverter(new Converter<Subject, String>() {

			private static final long serialVersionUID = -8369613381825007136L;

			@Override
			public Result<String> convertToModel(Subject value, ValueContext context) {
				return value != null ? Result.ok(value.getName()) : Result.ok("");
			}

			@Override
			public Subject convertToPresentation(String value, ValueContext context) {
				return SearchView.this.allSubjects.stream().filter(s -> s.getName().equals(value)).findFirst()
						.orElse(null);
			}
		}).bind(q -> q.getSubjectName(), (q, v) -> q.setSubjectName(v));
		this.queryBinder.forField(this.teacherNameField).bind(SearchQuery::getTeacherName, SearchQuery::setTeacherName);
		this.queryBinder.forField(this.distanceField).withNullRepresentation("")
				.withValidator(Validator.from(v -> v == null || NumberUtils.isDigits(v), "Nem megfelelő szám",
						ErrorLevel.ERROR))
				.withConverter(new StringToIntegerConverter(null, "Nem megfelelő szám"))
				.withConverter(new Converter<Integer, Integer>() {

					private static final long serialVersionUID = 1558771242492622793L;

					@Override
					public Result<Integer> convertToModel(Integer value, ValueContext context) {
						return value != null ? Result.ok(Integer.valueOf(value * 1000)) : Result.ok(Integer.valueOf(0));
					}

					@Override
					public Integer convertToPresentation(Integer value, ValueContext context) {
						return value != null ? Integer.valueOf(value / 1000) : null;
					}

				}).bind(SearchQuery::getMaxDistance, SearchQuery::setMaxDistance);

		this.queryBinder.setBean(this.query == null ? new SearchQuery() : this.query);

	}

	private boolean isAuthorizedAccess() {
		return this.authService.checkIfUserLoggedIn();
	}

}
