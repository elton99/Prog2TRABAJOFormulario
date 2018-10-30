/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenfinal;

import Persona.Persona;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
//import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.StageStyle;
//import javafx.stage.StageStyle;

public class VistaController implements Initializable {

    private Label label;
    //Declaramos la tabla y las columnas//
    @FXML
    private TableView<Persona> tablaPersonas;//Se instancia para obtener los atributos de la clase Persona// 
    @FXML
    private TableColumn<Persona, String> nombreCL;
    @FXML
    private TableColumn<Persona, String> apellidoCL;
    @FXML
    private TableColumn<Persona, Integer> ciCL;
    @FXML
    private TableColumn<Persona, String> telefonoCL;
    @FXML
    private TableColumn<Persona, String> ciudadCL;

    private final ObservableList<Persona> personas = FXCollections.observableArrayList();//Almacena los datos en forma de Lista de arreglos//
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
    private void aniadir(ActionEvent event) {
        Persona persona = new Persona();
        persona.setNombre(nombreTF.getText());//lo que haya en el nombreTF que me lo traiga como texto(getText) y lo ponga en el atributo nombre
        persona.setApellido(apellidoTF.getText());
        persona.setCi(Integer.parseInt(ciTF.getText()));//en vez de getText se usa parseInt para valores entero//
        persona.setTelefono(telefonoTF.getText());
        persona.setCiudad(ciudadTF.getText());
        personas.add(persona);//Permite añadir al ObservableList los datos//
    }

    @FXML
    private void modificar(ActionEvent event) {
        Persona persona = new Persona();
        persona.setNombre(nombreTF.getText());//lo que haya en el nombreTF que me lo traiga como texto(getText) y lo ponga en el atributo nombre
        persona.setApellido(apellidoTF.getText());
        persona.setCi(Integer.parseInt(ciTF.getText()));//en vez de getText se usa parseInt para valores entero//
        persona.setTelefono(telefonoTF.getText());
        persona.setCiudad(ciudadTF.getText());
        personas.set(posicionPersonaEnTabla, persona);//Se selecciona una fila en la tabla y trae todos los datos guardados de esa fila para poder modificar//
    }

    @FXML
    private void eliminar(ActionEvent event) {
        personas.remove(posicionPersonaEnTabla);
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
    private final ListChangeListener<Persona> selectorTablaPersonas = new ListChangeListener<Persona>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Persona> c) {
            ponerPersonaSeleccionada();
        }

    };

    public Persona getTablaPersonasSeleccionada() {
        if (tablaPersonas != null) {
            List<Persona> tabla = tablaPersonas.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final Persona competicionSeleccionada = tabla.get(0);
                return competicionSeleccionada;
            }
        }
        return null;
    }

    private void ponerPersonaSeleccionada() {
        final Persona persona = getTablaPersonasSeleccionada();
        posicionPersonaEnTabla = personas.indexOf(persona);
        if (persona != null) {
            //Se pone los TextField con los Datos Correspondientes//
            nombreTF.setText(persona.getNombre());
            apellidoTF.setText(persona.getApellido());
            ciTF.setText(persona.getCi().toString());
            telefonoTF.setText(persona.getTelefono());
            ciudadTF.setText(persona.getCiudad());
            //Se pone los botones en su estado correspondiente//
            modificarBT.setDisable(false);
            eliminarBT.setDisable(false);
            aniadirBT.setDisable(true);

        }
    }

    public void inicializarTablaPersonas() {
        tablaPersonas.setItems(personas);
        nombreCL.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidoCL.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        ciCL.setCellValueFactory(new PropertyValueFactory<>("ci"));
        telefonoCL.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        ciudadCL.setCellValueFactory(new PropertyValueFactory<>("ciudad"));

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
        BuscarDato.comparatorProperty().bind(tablaPersonas.comparatorProperty());

        // 5. Trae los datos de las personas ordenados a las filas de la tabla.
        tablaPersonas.setItems(BuscarDato);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            nombreTF.requestFocus();
        });
        this.inicializarTablaPersonas();
        modificarBT.setDisable(true);
        eliminarBT.setDisable(true);

        //Seleccionar las tuplas de la tabla persona(Tupla: Es la secuencia de valores agrupados en la tabla)//
        final ObservableList<Persona> tablaPersonaSel = tablaPersonas.getSelectionModel().getSelectedItems();
        tablaPersonaSel.addListener(selectorTablaPersonas);
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
    private void aniadir(javafx.scene.input.KeyEvent event) {
    }

}
