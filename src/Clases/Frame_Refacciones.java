package Clases;
import Conexiones.conectar;
import Interfaz.JIFR_Inventario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Frame_Refacciones {
    
    /* Declaramos la variable para hacer conexion a la DB */
    conectar c = new conectar();
    Connection con = c.conexion();
    
    //Variables
    String clave;
    /* bandera para activar cuando se presione el boton de nuevo*/
    public static boolean nuevo = false;
    
    public void principal()
    {
        JIFR_Inventario.txtid_ref.setEditable(false);
        limpiar();
        bloquear();
        mostrar_datos("");
    }
    
    /* limpiamos todos los campos de texto */
    public void limpiar()
    {
        JIFR_Inventario.txtid_ref.setText("");
        JIFR_Inventario.txtnom_ref.setText("");
        JIFR_Inventario.txtmarca_ref.setText("");
        JIFR_Inventario.txtex_ref.setText("");
        JIFR_Inventario.txtcosto_ref.setText("");
        JIFR_Inventario.txtdesc_ref.setText("");
    }
    
    /* bloquear todos los botones y campos de texto */
    public void bloquear()
    {
        JIFR_Inventario.btnnuevo_ref.setEnabled(true);
        JIFR_Inventario.btnagregar_ref.setEnabled(false); 
        JIFR_Inventario.btnactualizar_ref.setEnabled(false);
        JIFR_Inventario.btneliminar_ref.setEnabled(false);
        JIFR_Inventario.btncancelar_ref.setEnabled(false);
        
        JIFR_Inventario.txtid_ref.setEnabled(false);
        JIFR_Inventario.txtnom_ref.setEnabled(false);
        JIFR_Inventario.txtmarca_ref.setEnabled(false);
        JIFR_Inventario.txtex_ref.setEnabled(false);
        JIFR_Inventario.txtcosto_ref.setEnabled(false);
        JIFR_Inventario.txtdesc_ref.setEnabled(false);
        JIFR_Inventario.btnmas_ref.setEnabled(false);
        JIFR_Inventario.btnmenos_ref.setEnabled(false);
    }
   
/* desbloqueamos todos los botones y campos de texto */
    public void desbloquear()
    {
        JIFR_Inventario.btnnuevo_ref.setEnabled(false);
        JIFR_Inventario.btnagregar_ref.setEnabled(true);
        JIFR_Inventario.btnactualizar_ref.setEnabled(false);
        JIFR_Inventario.btneliminar_ref.setEnabled(false);
        JIFR_Inventario.btncancelar_ref.setEnabled(true);
        
        JIFR_Inventario.txtid_ref.setEnabled(true);
        JIFR_Inventario.txtnom_ref.setEnabled(true);
        JIFR_Inventario.txtmarca_ref.setEnabled(true);
        JIFR_Inventario.txtex_ref.setEnabled(true);
        JIFR_Inventario.txtcosto_ref.setEnabled(true);
        JIFR_Inventario.txtdesc_ref.setEnabled(true);
        JIFR_Inventario.btnmas_ref.setEnabled(true);
        JIFR_Inventario.btnmenos_ref.setEnabled(true);
    }
    
    void habilitar_txts()
    {
        JIFR_Inventario.txtid_ref.setEnabled(true);
        JIFR_Inventario.txtnom_ref.setEnabled(true);
        JIFR_Inventario.txtmarca_ref.setEnabled(true);
        JIFR_Inventario.txtex_ref.setEnabled(true);
        JIFR_Inventario.txtcosto_ref.setEnabled(true);
        JIFR_Inventario.txtdesc_ref.setEnabled(true);
        JIFR_Inventario.btnmas_ref.setEnabled(true);
        JIFR_Inventario.btnmenos_ref.setEnabled(true);
    }
    
    //Metodo para OBTENER y GENERAR la clave del servicio
    void obtener_clave()
    {
        //Instanciamos la clase que generara la clave
        gen_clave objgen = new gen_clave(); 
        
        //LLAMAMOS EL METODO CONSULTAR ULTIVA CLAVE GUARDADA EN LA BASE DE DATOS
        //MANDAMOS COMO PARAMETROS EL ATRIBUTO DE LA CLAVE DE LA BASE DE DATOS Y LA TABLA EN CUAL QUEREMOS BUSCAR LA CLAVE
        String ult_codigo = objgen.cons_ult_clave("clave_proc", "inventario");           
        System.out.println(ult_codigo);
        //verificamos si existen claves en la base de datos, si es null(si no hay claves) nos vamos al else y asignamos una nosotros
        if(ult_codigo != null)
        {        
            ult_codigo = ult_codigo.substring(3,8);//CON EL SUBSTRING SEPARAMOS LA CLAVE PARA QUE SOLO QUEDE EL PURO NUMERO(CLI-00001)
            //UNA VEZ OBTENIDO EL NUMERO LO MANDAMOS A UN METODO PARA QUE INCREMENTE A 1
            clave = objgen.sumar_clave("ref",ult_codigo);
        }
        else
        {
            clave = "ref00001";
        }
    }
    
    //Metodo para CONSULTAR los datos
    public void mostrar_datos(String dato)
    {
        //variable para bloq txtbuscar si no hay registros
        boolean bloq_buscar=true;
        
        //Declaramos los encabezados que tendra la tabla en un arreglo
        String encabezados[] =  {"Id Refaccion", "Nombre", "Marca", "Existencia", "Costo", "Descripcion"};
        String registros[] = new String [6];    //declaramos el arreglo donde se guardaran los registros
     
        DefaultTableModel model = new DefaultTableModel();  
        model = new DefaultTableModel(null, encabezados);   //Metemos los encabezados en el modelo de tabla
        /*
        model.addColumn("Clave");
        model.addColumn("Nombre");
        t_datos.setModel(model);
        */
      
        try 
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from inventario where CONCAT(clave_proc, nom_proc, marc_proc, existe_proc,"
                    + "cost_proc,desc_proc) like '%"+ dato +"%'");
            
            while(rs.next())
            {
                bloq_buscar = false; //hacemos referencia a no bloq el txtbuscar, ya que hay registros
                //guardamos los registros en nuestro arreglo
                registros[0] = rs.getString("clave_proc");  //decimos que se obtenga el dato del atributo clave_serv
                registros[1] = rs.getString("nom_proc");
                registros[2] = rs.getString("marc_proc");
                //No saltamos el registro tipo porq ese no lo queremos mostrar en tabla
                registros[3] = rs.getString("existe_proc");
                registros[4] = rs.getString("cost_proc");
                registros[5] = rs.getString("desc_proc");
               
                model.addRow(registros);    //añadimos nuestro arreglo al modelo de tabla 
            }
            JIFR_Inventario.tbrefacciones.setModel(model);    //le añadimos a NUESTRA TABLA todo el modelo anterior
            
            //CORREGIR ESTA PARTE, YA QUE FUNCIONA INCORRECTAMENTE
            /*if(bloq_buscar == false)
            {
                //Si hay algun registro habilitamos el txt de buscar
                txtbuscar.setEnabled(true);
            }
            else
            {
                //si no hay ningun registro desabilitamos el txt de buscar
                txtbuscar.setEnabled(false);
            }*/
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(JIFR_Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    public void registrar()
    {
        String nom, marca, tipo, existencia, costo, descripcion; 
        nom = JIFR_Inventario.txtnom_ref.getText();
        marca = JIFR_Inventario.txtmarca_ref.getText();
        tipo="Refaccion";
        existencia=JIFR_Inventario.txtex_ref.getText();
        costo=JIFR_Inventario.txtcosto_ref.getText();
        descripcion=JIFR_Inventario.txtdesc_ref.getText();
        
        //Procedimiento para insertar datos en la tabla servicio
        String consulta_sql = "insert into inventario values (?,?,?,?,?,?,?)";
        //sql = "INSERT INTO clientes (Id_cli,nom_cli,ape_cli,tel_cli,dir_cli,email_cli,rfc_cli,cel_cli,ciu_cli,est_cli,col_cli,cp_cli) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try 
        {
            PreparedStatement psd = con.prepareStatement(consulta_sql);
            psd.setString(1,clave);
            psd.setString (2, nom);
            psd.setString(3, marca);
            psd.setString(4, tipo);
            psd.setString (5, existencia);
            psd.setString (6,costo);
            psd.setString (7, descripcion);
            int grd = psd.executeUpdate();
            
            //Si se guardo con exito entramos al if
            if(grd>0)
            {
                JOptionPane.showMessageDialog(null, "Refaccion " +  nom +  " guardada con exito", "Registro Completado", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                bloquear();
                nuevo=false;
            }
            
        } 
        catch (SQLException ex) {
            //Logger.getLogger(JIFR_Servicios.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }
    
    //Metodo para pasar todos los datos de la tabla a los campos de texto
    public void datos_campos()
    {
        if( nuevo == false )    //Si el boton nuevo NO ESTA PRESIONADO entra
            {
                int fila = JIFR_Inventario.tbrefacciones.getSelectedRow();    //Guardamos la fila que se esta seleccionando (de la tabla) en una variable

                if (fila >= 0)  //si fila es igual o mayor que 0 quiere decir que una de las filas son seleccionadas
                {
                    //obtenemos el texto de cada una de las columnas y las escibimos en cada campo de texto
                    JIFR_Inventario.txtid_ref.setText(JIFR_Inventario.tbrefacciones.getValueAt(fila, 0).toString()); //obtenemos el texto del num de fila y columna
                    JIFR_Inventario.txtnom_ref.setText(JIFR_Inventario.tbrefacciones.getValueAt(fila,1).toString());
                    JIFR_Inventario.txtmarca_ref.setText(JIFR_Inventario.tbrefacciones.getValueAt(fila, 2).toString());
                    JIFR_Inventario.txtex_ref.setText(JIFR_Inventario.tbrefacciones.getValueAt(fila, 3).toString());
                    JIFR_Inventario.txtcosto_ref.setText(JIFR_Inventario.tbrefacciones.getValueAt(fila, 4).toString());
                    JIFR_Inventario.txtdesc_ref.setText(JIFR_Inventario.tbrefacciones.getValueAt(fila, 5).toString());
                    
                    habilitar_txts();   //habilitamos todos los campos de texto para poder sobrescribir en ellos
                    JIFR_Inventario.btnactualizar_ref.setEnabled(true);  //habilitamos el boton para poder actualizar
                    JIFR_Inventario.btneliminar_ref.setEnabled(true);  //habilitamos el boton para poder eliminar
                }   
            }
    }
    
    //metodo para modificar un registro
    public void modificar()
    {
        String nom, marca, tipo, existencia, costo, descripcion; 
        nom = JIFR_Inventario.txtnom_ref.getText();
        marca = JIFR_Inventario.txtmarca_ref.getText();
        tipo="Refaccion";
        existencia=JIFR_Inventario.txtex_ref.getText();
        costo=JIFR_Inventario.txtcosto_ref.getText();
        descripcion=JIFR_Inventario.txtdesc_ref.getText();
    
        try 
        {
           //consulta de ACTUALIZAR en MySQL
            String consulta_modificar = "UPDATE inventario SET nom_proc='"+nom+"',marc_proc='"+marca
            +"', tipo_proc='"+tipo+"', existe_proc='"+existencia+"', cost_proc='"+costo+"', desc_proc='"+descripcion
            + "' WHERE clave_proc = '"+ JIFR_Inventario.txtid_ref.getText() +"'";

            PreparedStatement psd = con.prepareStatement( consulta_modificar );
            int grd = psd.executeUpdate();  //ejecutamos la consulta
            
            if( grd > 0)    //si se completo la modificacion entra al if
            {
                JOptionPane.showMessageDialog(null, "Refaccion " + JIFR_Inventario.txtnom_ref.getText() +  " modificada con exito", "Registro Modificado", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                bloquear();
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(JIFR_Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eliminar()
    {
        try 
        {
            PreparedStatement psd = con.prepareStatement("DELETE FROM inventario WHERE clave_proc='"+ JIFR_Inventario.txtid_ref.getText() +"'");
            int grd = psd.executeUpdate();  //ejecutamos la consulta
            
            if( grd > 0)    //si se completo la eliminacion entra al if
            {
                JOptionPane.showMessageDialog(null, "Refaccion " + JIFR_Inventario.txtnom_ref.getText() +  " eliminada con exito", "Registro Eliminado", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                bloquear();
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(JIFR_Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void acc_btnnuevo()
    {
        nuevo=true;
        desbloquear();
        limpiar();
        obtener_clave();    //obtenemos la clave automaticamente con nuestros metodos
        JIFR_Inventario.txtid_ref.setText(clave);  //escribimos la clave en el campo del ID
        JIFR_Inventario.txtnom_ref.requestFocus();  //le damos el foco al campo nombre_servicio
    }
    
    public void acc_btncancelar()
    {
        nuevo=false;
        bloquear();
        limpiar();
    }
    
}