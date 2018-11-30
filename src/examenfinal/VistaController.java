package examenfinal;
import Persona.Persona;
import Profesor.Profesores;
//import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
//import javafx.scene.input.KeyCode;
//import javafx.stage.StageStyle;
//import javafx.stage.StageStyle;

public class VistaController implements Initializable {
    
    EntityManagerFactory emf=Persistence.createEntityManagerFactory("ExamenFinalPU");
    EntityManager em =emf.createEntityManager();

    private Label label;
    //Declaramos la tabla y las columnas//
    @FXML
    private TableView<Profesores> tablaPersonas;//Se instancia para obtener los atributos de la clase Persona// 
    @FXML
    private TableColumn<Profesores, String> nombreCL;
    @FXML
    private TableColumn<Profesores, String> apellidoCL;
    @FXML
    private TableColumn<Profesores, Integer> ciCL;
    @FXML
    private TableColumn<Profesores, String> telefonoCL;
    @FXML
    private TableColumn<Profesores, String> ciudadCL;

    private final ObservableList<Persona> personas = FXCollections.observableArrayList();//Almacena los datos en forma de Lista de arreglos//
    private final ObservableList<Profesores> lista = FXCollections.observableArrayList();//Almacena los datos en forma de Lista de arreglos//
    private final List<Persona> listaPersona = new ArrayList<>();
    private int posicionPersonaEnTabla;//Se declara una variable de tipo entero (identifica la posicion o indice de la persona en la tabla)
    //Declaramos los TextField//
    @FXML
    private TextField nombreTF;
    @FXML
    private TextField apellidoTF;
    @FXML
    private TextField ciTF;
    @FXML
    private TextField telefonoTF;
    @FXML
    private TextField ciudadTF;
    //Declaramos los Botones//
    @FXML
    private Button aniadirBT;
    @FXML
    private Button modificarBT;
    @FXML
    private Button eliminarBT;
    @FXML
    private Button nuevoBT;
    @FXML
    private TextField BuscarTF;
    @FXML
    private RadioButton buttonMasculino;
    @FXML
    private RadioButton buttonFemenino;
    @FXML
    private AnchorPane raiz;
    
   // List<Profesores> lista=new ArrayList();
    @FXML
    private void aniadir() {
        em.getTransaction().begin();
        Profesores persona = new Profesores();
        persona.setNombre(nombreTF.getText());//lo que haya en el nombreTF que me lo traiga como texto(getText) y lo ponga en el atributo nombre
        persona.setApellido(apellidoTF.getText());
        persona.setCi(ciTF.getText());//en vez de getText se usa parseInt para valores entero//
        persona.setTelefono(telefonoTF.getText());
        persona.setDireccion(ciudadTF.getText());
        em.persist(persona);
        em.getTransaction().commit();
        
        Platform.runLater(() -> {
            this.iniciarDatos();
            //em.close();
        });
        // Integer ci = Integer.parseInt(ciTF.getText());
        // persona.setCi(ci);
      
    }

    @FXML
    private void modificar(ActionEvent event) {
        em.getTransaction().begin();
        
       // lista.set(posicionPersonaEnTabla, persona);//Se selecciona una fila en la tabla y trae todos los datos guardados de esa fila para poder modificar//
        //em.persist(persona);
        em.getTransaction().commit();
        Platform.runLater(() ->{
        this.iniciarDatos();
    });
    }

    @FXML
    private void eliminar() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("ELIMINAR PROFESORES");
        alert.setHeaderText("ALERTA, ALERTA, ALERTA");
        alert.setContentText("Estás seguro de eliminar el registro?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            em.getTransaction().begin();
        Profesores p = tablaPersonas.getSelectionModel().getSelectedItem();
        System.out.println("Se ha seleccionado: " + p.getApellido());
        em.remove(p);
        em.getTransaction().commit();
        Platform.runLater(() -> {
            this.iniciarDatos();
        });
        } else {
            System.out.println("NO");
        }

        
    }

    @FXML
    private void nuevo(ActionEvent event) {
        nombreTF.setText("");
        apellidoTF.setText("");
        ciTF.setText("");
        telefonoTF.setText("");
        ciudadTF.setText("");
        modificarBT.setDisable(true);//Disable true (No da la opcion de modificar)
        eliminarBT.setDisable(true);//Disable true (No da la opcion de eliminar)
        aniadirBT.setDisable(false);//Disable false (Da la opcion de añadir)
    }
    //Para selleccionar una celda en la tablaPersona//
    private final ListChangeListener<Persona> selectorTablaPersonas = (ListChangeListener.Change<? extends Persona> c) -> {
        ponerPersonaSeleccionada();
    };

    public Profesores getTablaPersonasSeleccionada() {
        if (tablaPersonas != null) {
            List<Profesores> tabla = tablaPersonas.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final Profesores competicionSeleccionada = tabla.get(0);
                return competicionSeleccionada;
            }
        }
        return null;
    }

    private void ponerPersonaSeleccionada() {
        final Profesores persona = getTablaPersonasSeleccionada();
        posicionPersonaEnTabla = personas.indexOf(persona);
        if (persona != null) {
            //Se pone los TextField con los Datos Correspondientes//
            nombreTF.setText(persona.getNombre());
            apellidoTF.setText(persona.getApellido());
            ciTF.setText(persona.getCi());
            telefonoTF.setText(persona.getTelefono());
            ciudadTF.setText(persona.getDireccion());
            //Se pone los botones en su estado correspondiente//
            modificarBT.setDisable(false);
            eliminarBT.setDisable(false);
            aniadirBT.setDisable(true);

        }
    }

    public void inicializarTablaPersonas() {
        tablaPersonas.setItems(lista);
        nombreCL.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidoCL.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        ciCL.setCellValueFactory(new PropertyValueFactory<>("ci"));
        telefonoCL.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        ciudadCL.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        FilteredList<Persona> Centinela = new FilteredList<>(personas, persona -> true);
        BuscarTF.textProperty().addListener((Observar, Lista, Evaluar) -> {
            Centinela.setPredicate(persona -> {
                // El componente TextField(Buscar) esta vacio muestra los datos de las personas en la tabla//
                if (Evaluar == null || Evaluar.isEmpty()) {
                    return true;
                }

                // Compara campos de la tabla, condicionales creadas para nombre, apellido y ciudad
                String lowerCaseFilter = Evaluar.toLowerCase();

                if (persona.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Al ingresar el nombre empieza a buscar en la tabla y lo trae
                } else if (persona.getApellido().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Al ingresar el apellido empieza a buscar en la tabla y lo trae
                } else if (persona.getCiudad().toLowerCase().contains(lowerCaseFilter)) {
                    return true;    // Al ingresar la ciudad empieza a buscar en la tabla y lo trae 
                }
                return false; // si no hay nada coincidencias en nombre,apellido y ciudad la tabla desaparece por un momento hasta que se deje vacio la parte de buscar
            });
        });

        // 3. Asignacion de la lista FilteredList a SortedList(libreria) 
        SortedList<Persona> BuscarDato = new SortedList<>(Centinela);

        // 4.crea una conexion ordenada de la lista de personas con textfield de buscar
       // BuscarDato.comparatorProperty().bind(tablaPersonas.comparatorProperty());

        // 5. Trae los datos de las personas ordenados a las filas de la tabla.
       // tablaPersonas.setItems(BuscarDato);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.iniciarDatos();
        Platform.runLater(() -> {
        nombreTF.requestFocus();
        });
        this.inicializarTablaPersonas();
        modificarBT.setDisable(true);
        //eliminarBT.setDisable(true);

        //Seleccionar las tuplas de la tabla persona(Tupla: Es la secuencia de valores agrupados en la tabla)//
      //  final ObservableList<Persona> tablaPersonaSel = tablaPersonas.getSelectionModel().getSelectedItems();
       // tablaPersonaSel.addListener(selectorTablaPersonas);
    }
    
    public void iniciarDatos(){
        lista.clear();
        TypedQuery<Profesores> consulta = em.createQuery("SELECT p FROM Profesores p ", Profesores.class);
        lista.addAll(consulta.getResultList());
        
    }
    public void modificarlista() {
        Profesores persona = tablaPersonas.getSelectionModel().getSelectedItem();
        System.out.println("Nombre: " + persona.getNombre());
        nombreTF.setText(persona.getNombre());
        modificarBT.setDisable(false);
        eliminarBT.setDisable(false);
        //persona.setNombre(nombreTF.getText());//lo que haya en el nombreTF que me lo traiga como texto(getText) y lo ponga en el atributo nombre
        //persona.setApellido(apellidoTF.getText());
        //persona.setCi(ciTF.getText());//en vez de getText se usa parseInt para valores entero//
        //persona.setTelefono(telefonoTF.getText());
        //persona.setDireccion(ciudadTF.getText());
    }

    @FXML
    public void Masculino() {
        if (buttonMasculino.isSelected()) {

            buttonFemenino.setSelected(false);
        }
    }

    @FXML
    private void Femenino(ActionEvent event) {
        if (buttonFemenino.isSelected()) {
            buttonMasculino.setSelected(false);
        }
    }

    @FXML
    private void ani() {
        KeyCombination btonuevo = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN);
        raiz.setOnKeyPressed((KeyEvent event) -> {
            this.aniadir();
        });
    }
    

}
