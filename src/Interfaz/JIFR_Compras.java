//Modulo Compras
package Interfaz;

import Conexiones.conectar;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class JIFR_Compras extends javax.swing.JInternalFrame {

    /* Declaramos la variable para hacer conexion a la DB */
    conectar c = new conectar();
    Connection con = c.conexion();
    //instanciamientos (objetos) de clases
    static JFR_Principal p;
    
    //Variables
    String clave;
    /* bandera para activar cuando se presione el boton de nuevo*/
    public static boolean nuevo = false;
    DefaultTableModel model = new DefaultTableModel();
    String registro [] = new String [6]; //registro para almacenar los atributos de la tabla
    static String id,nombre,marca,descripcion,costo;
    int total = 0;
    
    //recibimos el obj de la clase principal, para poder abrir otro IJFR al escritorio
    public JIFR_Compras(JFR_Principal p) 
    {
        initComponents();
        
        this.p = p; //igualamos el objeto recibido (princpal) con nuetro obj que creamos en esta clase
        txtid.setEditable(false);
        txttotal.setEditable(false);
        txtprod.setEditable(false);
        limpiar();
        bloquear();
        estructura_tabla();
        System.out.println(this.p);
    }
    
    //Metodo para poder sobrescribir en JDateChooser (txtfecha)
    public java.util.Date StringADate(String fecha)
    {
        SimpleDateFormat formato= new SimpleDateFormat("dd-MMM-yyyy");
        Date fechaE=null;
        
        try{
            fechaE = formato.parse(fecha);
            return fechaE;
        }
        catch(ParseException ex)
        {
            return null;
        }
    }

    //obtenemos la fecha que seleccionamos en el JDateChooser
    public String obt_fecha()
    {
        DateFormat df = DateFormat.getDateInstance();
        String fecha = df.format(txtfecha.getDate());   //Guardamos en String la fecha       
        return fecha;
    }
    
    /* limpiamos todos los campos de texto */
    public void limpiar()
    {
        txtid.setText("");
        txtprov.setText("");
        txtfecha.setDate(StringADate(""));
        txtprod.setText("");
        txtcant.setText("");
        txttotal.setText("");
    }
    
    public void limpiar_detalle()
    {
        txtprod.setText("");
        txtcant.setText("");
    }
    
    /* bloquear todos los botones y campos de texto */
    public void bloquear()
    {
        btnnuevo.setEnabled(true);
        btnagregar.setEnabled(false); 
        btnactualizar.setEnabled(false);
        btneliminar.setEnabled(false);
        btncancelar.setEnabled(false);
        
        txtid.setEnabled(false);
        txtprov.setEnabled(false);
        txtprod.setEnabled(false);
        txtcant.setEnabled(false);
        txttotal.setEnabled(false);
        txtfecha.setEnabled(false);
        btnmas.setEnabled(false);
        btnmenos.setEnabled(false);
        tbprod.setEnabled(false);
        btnbusc_prod.setEnabled(false);
    }
   
/* desbloqueamos todos los botones y campos de texto */
    public void desbloquear()
    {
        btnnuevo.setEnabled(false);
        btnagregar.setEnabled(true);
        btnactualizar.setEnabled(false);
        btneliminar.setEnabled(false);
        btncancelar.setEnabled(true);
        
        txtid.setEnabled(true);
        txtprov.setEnabled(true);
        txtprod.setEnabled(true);
        txtcant.setEnabled(true);
        txttotal.setEnabled(true);
        txtfecha.setEnabled(true);
        btnmas.setEnabled(true);
        btnmenos.setEnabled(true);
        tbprod.setEnabled(true);
        btnbusc_prod.setEnabled(true);
    }
    
    void habilitar_txts()
    {
        txtid.setEnabled(true);
        txtprov.setEnabled(true);
        txtprod.setEnabled(true);
        txtcant.setEnabled(true);
        txttotal.setEnabled(true);
        txtfecha.setEnabled(true);
        btnmas.setEnabled(true);
        btnmenos.setEnabled(true);
        tbprod.setEnabled(true);
        btnbusc_prod.setEnabled(true);
    }
    
    void estructura_tabla()
    {
        //Declaramos los encabezados que tendra la tabla en un arreglo
        String encabezados[] =  {"Id Producto", "Nombre", "Marca", "Descripcion", "Cantidad", "Importe"};
      
        model = new DefaultTableModel(null, encabezados);   //Metemos los encabezados en el modelo de tabla
        tbprod.setModel(model);    //le añadimos a NUESTRA TABLA todo el modelo anterior

    }
    
    static void get_prod(String id_prod, String nom, String marc, String desc, String cost)
    {
        //recibimos la informacion del producto seleccionado
        id = id_prod;
        nombre = nom;
        marca= marc;
        descripcion=desc;
        costo = cost;
        //escribimos en el txtprod el id y nombre del producto seleccioado
        txtprod.setText(id + " - " + nombre);
    }
    
    void agr_prod()
    {
        //obetenemos la cant de producto comprado para multiplicar por su valor, y obtener importe
        int cantidad = Integer.parseInt(txtcant.getText());
        int importe = Integer.parseInt(costo) * cantidad;
        
        registro[0] = this.id; 
        registro[1] = this.nombre;
        registro[2] = this.marca;
        registro[3] = this.descripcion;
        registro[4] = txtcant.getText();
        registro[5] = String.valueOf(importe);
        model.addRow(registro);    //añadimos nuestro arreglo al modelo de tabla
        tbprod.setModel(model);    //le añadimos a NUESTRA TABLA todo el modelo anterior
        
        //vamos calculando el total cada vez q se agregue producto
        total = total + importe;
        txttotal.setText("$" + String.valueOf(total));
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbprod = new javax.swing.JTable();
        txtprod = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txttotal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtcant = new javax.swing.JTextField();
        btnmas = new javax.swing.JButton();
        btnmenos = new javax.swing.JButton();
        btnbusc_prod = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtfecha = new com.toedter.calendar.JDateChooser();
        txtprov = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        btnnuevo = new javax.swing.JButton();
        btnagregar = new javax.swing.JButton();
        btnactualizar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Compras");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle de Compra"));

        tbprod.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbprod);

        txtprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtprodActionPerformed(evt);
            }
        });

        jLabel33.setText("Producto:");

        jLabel3.setText("Total a Pagar:");

        jLabel1.setText("Cantidad:");

        btnmas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/signo + 2.png"))); // NOI18N
        btnmas.setText("Agregar Producto");
        btnmas.setBorder(null);
        btnmas.setBorderPainted(false);
        btnmas.setContentAreaFilled(false);
        btnmas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmasActionPerformed(evt);
            }
        });

        btnmenos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/signo -.png"))); // NOI18N
        btnmenos.setText("Quitar Producto");
        btnmenos.setBorder(null);
        btnmenos.setBorderPainted(false);
        btnmenos.setContentAreaFilled(false);
        btnmenos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnbusc_prod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lupa-de-busqueda (1).png"))); // NOI18N
        btnbusc_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbusc_prodActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprod, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnbusc_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcant, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 25, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnmas)
                            .addComponent(btnmenos)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel3)))
                        .addGap(0, 25, Short.MAX_VALUE))
                    .addComponent(txttotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnmas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnmenos, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel33)
                                .addComponent(jLabel1)
                                .addComponent(txtcant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnbusc_prod))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Compra"));

        jLabel30.setText("Id Compra:");

        jLabel2.setText("Proveedor:");

        jLabel4.setText("Fecha de Compra:");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprov, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(33, 33, 33)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtprov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

        btnnuevo.setText("Nuevo");
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });

        btnagregar.setText("Agregar");
        btnagregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregarActionPerformed(evt);
            }
        });

        btnactualizar.setText("Actualizar");
        btnactualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizarActionPerformed(evt);
            }
        });

        btncancelar.setText("Cancelar");
        btncancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarActionPerformed(evt);
            }
        });

        btneliminar.setText("Eliminar");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnactualizar)
                            .addComponent(btnagregar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(btnnuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnagregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnactualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btneliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btncancelar))
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lupa-de-busqueda (1).png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(403, 403, 403)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(403, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(159, 159, 159)
                    .addComponent(jButton1)
                    .addContainerGap(160, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtprodActionPerformed
   
    }//GEN-LAST:event_txtprodActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
    nuevo=true;
    desbloquear();
    limpiar();
    //obtener_clave();    //obtenemos la clave automaticamente con nuestros metodos
    txtid.setText(clave);  //escribimos la clave en el campo del ID
    txtprov.requestFocus();  //le damos el foco al campo nombre_servicio
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btnagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarActionPerformed
    
    }//GEN-LAST:event_btnagregarActionPerformed

    private void btnactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizarActionPerformed
     
    }//GEN-LAST:event_btnactualizarActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        nuevo=false;
        bloquear();
        limpiar();
    }//GEN-LAST:event_btncancelarActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
     
    }//GEN-LAST:event_btneliminarActionPerformed

    private void btnmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmasActionPerformed
        agr_prod(); //agregamos la inf. del pdocuto a la tabla
        limpiar_detalle();
    }//GEN-LAST:event_btnmasActionPerformed

    private void btnbusc_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbusc_prodActionPerformed
        try{
            JIFR_ConsInv cons = new JIFR_ConsInv();
            this.p.Escritorio.add(cons);
            cons.show();
        }
        catch(Exception x)
        {
            JOptionPane.showMessageDialog(null, "Error: " + x);
        }
    
    }//GEN-LAST:event_btnbusc_prodActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnactualizar;
    public static javax.swing.JButton btnagregar;
    private javax.swing.JButton btnbusc_prod;
    public static javax.swing.JButton btncancelar;
    public static javax.swing.JButton btneliminar;
    public static javax.swing.JButton btnmas;
    public static javax.swing.JButton btnmenos;
    public static javax.swing.JButton btnnuevo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbprod;
    private javax.swing.JTextField txtcant;
    private com.toedter.calendar.JDateChooser txtfecha;
    public static javax.swing.JTextField txtid;
    public static javax.swing.JTextField txtprod;
    private javax.swing.JTextField txtprov;
    private javax.swing.JTextField txttotal;
    // End of variables declaration//GEN-END:variables
}
