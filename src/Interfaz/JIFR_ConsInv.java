
package Interfaz;

import Conexiones.conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class JIFR_ConsInv extends javax.swing.JInternalFrame {
    
    /* Declaramos la variable para hacer conexion a la DB */
    conectar c = new conectar();
    Connection con = c.conexion();
    
    String id,nombre,marca,descripcion,costo;
    
    public JIFR_ConsInv() {
        
        initComponents();
        mostrar_datos("");
    }  
    
    //Metodo para CONSULTAR los datos
    public void mostrar_datos(String dato)
    {
        
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
            tbrefacciones.setModel(model);    //le añadimos a NUESTRA TABLA todo el modelo anterior
            
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
                
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JT_InterfazInventarios = new javax.swing.JTabbedPane();
        JP_Productos = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tbrefacciones = new javax.swing.JTable();
        txtbuscar_ref = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnsalir_ref = new javax.swing.JButton();
        btnaceptar_ref = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        JP_Herramientas = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbherramientas = new javax.swing.JTable();
        txtbuscar_her = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        JP_Consumibles = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tbconsumibles = new javax.swing.JTable();
        txtbuscar_con = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Inventario");

        JT_InterfazInventarios.setBorder(javax.swing.BorderFactory.createTitledBorder("Consultar Inventario"));

        tbrefacciones.setModel(new javax.swing.table.DefaultTableModel(
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
        tbrefacciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbrefaccionesMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tbrefacciones);

        txtbuscar_ref.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_refKeyReleased(evt);
            }
        });

        jLabel3.setText("Buscar:");

        btnsalir_ref.setText("Regresar");
        btnsalir_ref.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalir_refActionPerformed(evt);
            }
        });

        btnaceptar_ref.setText("Aceptar");
        btnaceptar_ref.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaceptar_refActionPerformed(evt);
            }
        });

        jLabel1.setText("Seleccione la refaccion que desea añadir a la compra.");

        javax.swing.GroupLayout JP_ProductosLayout = new javax.swing.GroupLayout(JP_Productos);
        JP_Productos.setLayout(JP_ProductosLayout);
        JP_ProductosLayout.setHorizontalGroup(
            JP_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_ProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JP_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_ProductosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnaceptar_ref)
                        .addGap(18, 18, 18)
                        .addComponent(btnsalir_ref))
                    .addGroup(JP_ProductosLayout.createSequentialGroup()
                        .addGroup(JP_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JP_ProductosLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtbuscar_ref, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        JP_ProductosLayout.setVerticalGroup(
            JP_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_ProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JP_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtbuscar_ref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JP_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsalir_ref)
                    .addComponent(btnaceptar_ref))
                .addContainerGap())
        );

        JT_InterfazInventarios.addTab("Refacciones", JP_Productos);

        tbherramientas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(tbherramientas);

        jLabel2.setText("Buscar:");

        jButton1.setText("Regresar");

        jButton2.setText("Aceptar");

        jLabel5.setText("Seleccione la herramienta que desea añadir a la compra.");

        javax.swing.GroupLayout JP_HerramientasLayout = new javax.swing.GroupLayout(JP_Herramientas);
        JP_Herramientas.setLayout(JP_HerramientasLayout);
        JP_HerramientasLayout.setHorizontalGroup(
            JP_HerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_HerramientasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JP_HerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_HerramientasLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(JP_HerramientasLayout.createSequentialGroup()
                        .addGroup(JP_HerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JP_HerramientasLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtbuscar_her, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        JP_HerramientasLayout.setVerticalGroup(
            JP_HerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_HerramientasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JP_HerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtbuscar_her, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JP_HerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        JT_InterfazInventarios.addTab("Herramientas", JP_Herramientas);

        tbconsumibles.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane14.setViewportView(tbconsumibles);

        jLabel4.setText("Buscar:");

        jButton3.setText("Regresar");

        jButton4.setText("Aceptar");

        jLabel6.setText("Seleccione el consumble que desea añadir a la compra.");

        javax.swing.GroupLayout JP_ConsumiblesLayout = new javax.swing.GroupLayout(JP_Consumibles);
        JP_Consumibles.setLayout(JP_ConsumiblesLayout);
        JP_ConsumiblesLayout.setHorizontalGroup(
            JP_ConsumiblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_ConsumiblesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JP_ConsumiblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_ConsumiblesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3))
                    .addGroup(JP_ConsumiblesLayout.createSequentialGroup()
                        .addGroup(JP_ConsumiblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JP_ConsumiblesLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtbuscar_con, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        JP_ConsumiblesLayout.setVerticalGroup(
            JP_ConsumiblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_ConsumiblesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JP_ConsumiblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtbuscar_con, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JP_ConsumiblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        JT_InterfazInventarios.addTab("Consumibles", JP_Consumibles);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JT_InterfazInventarios, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JT_InterfazInventarios)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtbuscar_refKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_refKeyReleased
        mostrar_datos(txtbuscar_ref.getText());
    }//GEN-LAST:event_txtbuscar_refKeyReleased

    private void tbrefaccionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbrefaccionesMouseClicked
        if( evt.getButton() == 1 )  //si se da click dentro de la tabla entra al if
        {   
            int fila = tbrefacciones.getSelectedRow();    //Guardamos la fila que se esta seleccionando (de la tabla) en una variable

            if (fila >= 0)  //si fila es igual o mayor que 0 quiere decir que una de las filas son seleccionadas
            {
                //obtenemos el texto de cada una de las columnas y las escibimos en cada campo de texto
                id = (tbrefacciones.getValueAt(fila, 0).toString()); //obtenemos el texto del num de fila y columna
                nombre = (tbrefacciones.getValueAt(fila,1).toString());
                marca = (tbrefacciones.getValueAt(fila, 2).toString());
                costo = (tbrefacciones.getValueAt(fila, 4).toString());
                descripcion = (tbrefacciones.getValueAt(fila, 5).toString());
            }   
           
        
        }        
    }//GEN-LAST:event_tbrefaccionesMouseClicked

    private void btnsalir_refActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalir_refActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnsalir_refActionPerformed

    private void btnaceptar_refActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaceptar_refActionPerformed
        int fila = tbrefacciones.getSelectedRow();    //Guardamos la fila que se esta seleccionando (de la tabla) en una variable
        //si hay alguna fila seleccionada entra al if
        if( fila != -1 ) 
        {
            //enviamos los atributos del producto seleccionado
            JIFR_Compras.get_prod(id, nombre, marca, descripcion, costo);
            dispose();
        }
        //si no hay ninguna fila seleccionada entra al else
        else
        {
            System.out.println("Seleccione producto a añadir compra");
        }
    }//GEN-LAST:event_btnaceptar_refActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JP_Consumibles;
    private javax.swing.JPanel JP_Herramientas;
    private javax.swing.JPanel JP_Productos;
    private javax.swing.JTabbedPane JT_InterfazInventarios;
    private javax.swing.JButton btnaceptar_ref;
    private javax.swing.JButton btnsalir_ref;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JTable tbconsumibles;
    private javax.swing.JTable tbherramientas;
    public static javax.swing.JTable tbrefacciones;
    private javax.swing.JTextField txtbuscar_con;
    private javax.swing.JTextField txtbuscar_her;
    public static javax.swing.JTextField txtbuscar_ref;
    // End of variables declaration//GEN-END:variables
}
