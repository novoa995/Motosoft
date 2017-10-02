//GOD WITH ME
package Interfaz;

import javax.swing.JOptionPane;
import Conexiones.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import Clases.gen_clave;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class JIFR_Servicios extends javax.swing.JInternalFrame {

/* Declaramos la variable para hacer conexion a la DB */
    conectar c = new conectar();
    Connection con = c.conexion();
    
 /* bandera para activar cuando halla un error en validacion y activar cuando se presione el boton de nuevo*/
    public static boolean err_validacion = false , nuevo = false;
    
    String clave;
    
    public JIFR_Servicios() {
        initComponents();
     
        cmp_idservicio.setEditable(false);
        limpiar();
        bloquear_all();
        mostrar_datos("");
    }

/* bloquear todos los botones y campos de texto */
    public void bloquear_all()
    {
        btn_nuevo.setEnabled(true);
        btn_agregar.setEnabled(false); 
        btn_actualizar.setEnabled(true);
        btn_eliminar.setEnabled(true);
        btn_cancelar.setEnabled(false);
        
        cmp_idservicio.setEnabled(false);
        cmp_nombreservicio.setEnabled(false);
        cmp_costoservicio.setEnabled(false);
        cmp_descripcion.setEnabled(false);
    }
   
/* desbloqueamos todos los botones y campos de texto */
    public void desbloquear_all()
    {
        btn_nuevo.setEnabled(false);
        btn_agregar.setEnabled(true);
        btn_actualizar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_cancelar.setEnabled(true);
        
        cmp_idservicio.setEnabled(true);
        cmp_nombreservicio.setEnabled(true);
        cmp_costoservicio.setEnabled(true);
        cmp_descripcion.setEnabled(true);
    }
    
    void habilitar_txts()
    {
        cmp_idservicio.setEnabled(true);
        cmp_nombreservicio.setEnabled(true);
        cmp_descripcion.setEnabled(true);
        cmp_costoservicio.setEnabled(true);
    }
    
/* limpiamos todos los campos de texto */
    public void limpiar()
    {
        cmp_idservicio.setText("");
        cmp_nombreservicio.setText("");
        cmp_costoservicio.setText("");
        cmp_descripcion.setText("");
    }

/* Validamos el campo del nombre del servicio */    
    public void validar_nombreServicio()
    {
        String nombre_servicio = cmp_nombreservicio.getText();

        /* comparamos si el nombre del servicio es mayor a 50 caracteres */        
        if ( nombre_servicio.length() > 50 )
        {
            JOptionPane.showMessageDialog(null, "Nombre del servicio demasiado largo", "Error en Nombre del Servicio", JOptionPane.ERROR_MESSAGE);
            err_validacion = true;
        }
    }
    
    
/* validamos que el campo costo solo acepte DATOS ENTEROS (numeros) y longitud menor que 6*/    
    public void validar_costo()
    {
        String costo = cmp_costoservicio.getText();  /* guardamos el texto en la variable */
   
        for(int x=0; x < costo.length();  x++)  /* ciclo para recorrer letra por letra del texto obtenido */
        {
            if ( costo.charAt(x) < '0' || costo.charAt(x) > '9')    /* comparamos caracter por caracter si en el texto NO HAY NUMEROS */
            {
                JOptionPane.showMessageDialog(null, "Costo Incorrecto", "Error Costo", JOptionPane.ERROR_MESSAGE);
                err_validacion = true;  /* activamos la BANDERA de ERROR de VALIDACION */
                break;
            }
        }

        /* comparamos si la longiutud del costo es mayor que 6 */        
        if( costo.length() > 6 )
        {
            JOptionPane.showMessageDialog(null, "Costo Exedido", "Error Costo", JOptionPane.ERROR_MESSAGE);
            err_validacion = true;
        }
        
    }
    
//validamos el campo de descripcion del servicio   
    public void validar_descripcion()
    {
        String descripcion = cmp_descripcion.getText();

        //comparamos si la longitud de la descripcion es mayor a 140 caracteres
        if(descripcion.length() > 140)
        {
            JOptionPane.showMessageDialog(null, "Descripcion demasiada larga", "Error en Descripcion del Servicio", JOptionPane.ERROR_MESSAGE);
            err_validacion = true;
        }
    }
    
    
    void validar_campos_nullos()
    {
        String nombre = cmp_nombreservicio.getText();
        String descripcion = cmp_descripcion.getText();
        String costo = cmp_costoservicio.getText();
        
        if( nombre.equals("") || descripcion.equals("") || costo.equals("") )
        {
            err_validacion = true;
            JOptionPane.showMessageDialog(null, "No puede dejar campos de texto vacios", "Error al Registrar", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    void registrar()
    {
        String nombre, costo, descripcion; 
        nombre = cmp_nombreservicio.getText();
        costo = cmp_costoservicio.getText();
        descripcion = cmp_descripcion.getText();
        
        //Procedimiento para insertar datos en la tabla servicio
        String consulta_sql = "insert into servicio values (?,?,?,?)";
        //sql = "INSERT INTO clientes (Id_cli,nom_cli,ape_cli,tel_cli,dir_cli,email_cli,rfc_cli,cel_cli,ciu_cli,est_cli,col_cli,cp_cli) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try 
        {
            PreparedStatement psd = con.prepareStatement(consulta_sql);
            psd.setString(1,clave);
            psd.setString (2, nombre);
            psd.setString(3, descripcion);
            psd.setString(4, costo);
            int grd = psd.executeUpdate();
            
            //Si se guardo con exito entramos al if
            if(grd>0)
            {
                JOptionPane.showMessageDialog(null, "Servicio " +  nombre +  " guardado con exito", "Registro Completado", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                bloquear_all();
            }
            
        } 
        catch (SQLException ex) {
            //Logger.getLogger(JIFR_Servicios.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    //Metodo para OBTENER y GENERAR la clave del servicio
    void obtener_clave()
    {
        //Instanciamos la clase que generara la clave
        gen_clave objgen = new gen_clave(); 
        
        //LLAMAMOS EL METODO CONSULTAR ULTIVA CLAVE GUARDADA EN LA BASE DE DATOS
        //MANDAMOS COMO PARAMETROS EL ATRIBUTO DE LA CLAVE DE LA BASE DE DATOS Y LA TABLA EN CUAL QUEREMOS BUSCAR LA CLAVE
        String ult_codigo = objgen.cons_ult_clave("clave_serv", "servicio");           
        
        //verificamos si existen claves en la base de datos, si es null(si no hay claves) nos vamos al else y asignamos una nosotros
        if(ult_codigo != null)
        {        
            ult_codigo = ult_codigo.substring(3,8);//CON EL SUBSTRING SEPARAMOS LA CLAVE PARA QUE SOLO QUEDE EL PURO NUMERO(CLI-00001)
            //UNA VEZ OBTENIDO EL NUMERO LO MANDAMOS A UN METODO PARA QUE INCREMENTE A 1
            clave = objgen.sumar_clave("srv",ult_codigo);
        }
        else
        {
            clave = "srv00001";
        }
    }
    
    //Metodo para CONSULTAR los datos
    void mostrar_datos(String dato)
    {
        //Declaramos los encabezados que tendra la tabla en un arreglo
        String encabezados[] =  {"Clave", "Nombre del Servicio", "Descripcion", "Costo del servicio" };
        String registros[] = new String [4];    //declaramos el arreglo donde se guardaran los registros
     
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
            ResultSet rs = st.executeQuery("select * from servicio where CONCAT(clave_serv, nom_serv, desc_serv, cost_serv) like '%"+ dato +"%'");
            
            while(rs.next())
            {
                //guardamos los registros en nuestro arreglo
                registros[0] = rs.getString("clave_serv");  //decimos que se obtenga el dato del atributo clave_serv
                registros[1] = rs.getString("nom_serv");
                registros[2] = rs.getString("desc_serv");
                registros[3] = rs.getString("cost_serv");   
                model.addRow(registros);    //añadimos nuestro arreglo al modelo de tabla 
            }
            t_datos.setModel(model);    //le añadimos a NUESTRA TABLA todo el modelo anterior
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(JIFR_Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    //metodo para modificar un registro
    void modificar()
    {
        String nombre = cmp_nombreservicio.getText();
        String descripcion = cmp_descripcion.getText();
        String costo = cmp_costoservicio.getText();
    
        try 
        {
           //consulta de ACTUALIZAR en MySQL
            String consulta_modificar = "UPDATE servicio SET nom_serv='"+ nombre +"', desc_serv='"+ descripcion 
                                       +"', cost_serv='"+ costo +"' WHERE clave_serv = '"+ cmp_idservicio.getText() +"'";

            PreparedStatement psd = con.prepareStatement( consulta_modificar );
            int grd = psd.executeUpdate();  //ejecutamos la consulta
            
            if( grd > 0)    //si se completo la modificacion entra al if
            {
                JOptionPane.showMessageDialog(null, "Servicio " + cmp_nombreservicio.getText() +  " modificado con exito", "Registro Modificado", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                bloquear_all();
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(JIFR_Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    void eliminar()
    {
        try 
        {
            PreparedStatement psd = con.prepareStatement("DELETE FROM servicio WHERE clave_serv='"+ cmp_idservicio.getText() +"'");
            int grd = psd.executeUpdate();  //ejecutamos la consulta
            
            if( grd > 0)    //si se completo la eliminacion entra al if
            {
                JOptionPane.showMessageDialog(null, "Servicio " + cmp_nombreservicio.getText() +  " eliminado con exito", "Registro Eliminado", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                bloquear_all();
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(JIFR_Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmp_idservicio = new javax.swing.JTextField();
        cmp_nombreservicio = new javax.swing.JTextField();
        cmp_costoservicio = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        cmp_descripcion = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        t_datos = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        cmp_buscar = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btn_nuevo = new javax.swing.JButton();
        btn_agregar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setClosable(true);
        setIconifiable(true);
        setTitle("Servicios Actuales");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Servicio"));

        jLabel1.setText("Id de Servicio:");

        jLabel2.setText("Nombre de Servicio:");

        jLabel3.setText("Costo del Servicio:");

        cmp_descripcion.setColumns(20);
        cmp_descripcion.setRows(5);
        jScrollPane1.setViewportView(cmp_descripcion);

        jLabel4.setText("Descripción del Servicio:");

        jLabel5.setText("$");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmp_idservicio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmp_nombreservicio, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmp_costoservicio, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(cmp_idservicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmp_nombreservicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmp_costoservicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Servicios Actuales"));

        t_datos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        t_datos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_datosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(t_datos);

        jLabel6.setText("Buscar:");

        cmp_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cmp_buscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmp_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmp_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

        btn_nuevo.setText("Nuevo");
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });

        btn_agregar.setText("Agregar");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });

        btn_actualizar.setText("Modificar");
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });

        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        btn_eliminar.setText("Eliminar");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_agregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_actualizar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_eliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_nuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btn_nuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_agregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_actualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_eliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        nuevo = true;   //activamos la bandera que indica que se presiono el btn NUEVO
        desbloquear_all();
        limpiar();
        obtener_clave();    //obtenemos la clave automaticamente con nuestros metodos
        cmp_idservicio.setText(clave);  //escribimos la clave en el campo del ID
        cmp_nombreservicio.requestFocus();  //le damos el foco al campo nombre_servicio
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        bloquear_all();
        limpiar();
        nuevo = false;
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        validar_nombreServicio();
        validar_costo();
        validar_descripcion();
        validar_campos_nullos();
        
        if(err_validacion == false) //si NO HAY ERROR registramos los datos
        {
            registrar();
            mostrar_datos("");  //cargamos nuestra tabla de mostrar datos
        }
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void cmp_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmp_buscarKeyReleased
        mostrar_datos(cmp_buscar.getText());    //cada vez que se ingrese un caracter cargamos los datos
    }//GEN-LAST:event_cmp_buscarKeyReleased

    private void t_datosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_datosMouseClicked
        if( evt.getButton() == 1 )  //si se da click dentro de la tabla entra al if
        {
            if( nuevo == false )    //Si el boton nuevo NO ESTA PRESIONADO entra
            {
                int fila = t_datos.getSelectedRow();    //Guardamos la fila que se esta seleccionando (de la tabla) en una variable

                if (fila >= 0)  //si fila es igual o mayor que 0 quiere decir que una de las filas son seleccionadas
                {
                    //obtenemos el texto de cada una de las columnas y las escibimos en cada campo de texto
                    cmp_idservicio.setText(t_datos.getValueAt(fila, 0).toString()); //obtenemos el texto del num de fila y columna
                    cmp_nombreservicio.setText(t_datos.getValueAt(fila,1).toString());
                    cmp_descripcion.setText(t_datos.getValueAt(fila, 2).toString());
                    cmp_costoservicio.setText(t_datos.getValueAt(fila, 3).toString());
                    
                    habilitar_txts();   //habilitamos todos los campos de texto para poder sobrescribir en ellos
                }   
            }
        
        }
    }//GEN-LAST:event_t_datosMouseClicked

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        if( !cmp_idservicio.getText().equals("") )  //Si el campo del ID NO ESTA VACIO, modificamos el registro
        {
            modificar();
            mostrar_datos("");  //cargamos nuestra tabla de mostrar datos
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Seleccione en la tabla un registro a modificar", "Error al Modificar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        String nombre = cmp_nombreservicio.getText();
        
        if( !cmp_idservicio.getText().equals("") )  //Si el campo del ID NO ESTA VACIO, eliminamos el registro
        {
            //preguntamos si esta seguro de elimnar, si si entramos al if y eliminamos el registro
            int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro que deseas eliminar el servicio "+ nombre +"?", "Alerta!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            //si=0    no=1
            if(resp == 0)
            {
                eliminar();
                mostrar_datos("");  //cargamos nuestra tabla de mostrar datos
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Seleccione en la tabla un registro a eliminar", "Error al eliminar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_eliminarActionPerformed

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JTextField cmp_buscar;
    private javax.swing.JTextField cmp_costoservicio;
    private javax.swing.JTextArea cmp_descripcion;
    private javax.swing.JTextField cmp_idservicio;
    private javax.swing.JTextField cmp_nombreservicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable t_datos;
    // End of variables declaration//GEN-END:variables
}
