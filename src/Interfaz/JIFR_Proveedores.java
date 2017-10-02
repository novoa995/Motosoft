package Interfaz;

import Clases.gen_clave;
import Conexiones.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class JIFR_Proveedores extends javax.swing.JInternalFrame {

    /* Declaramos la variable para hacer conexion a la DB */
    conectar c = new conectar();
    Connection con = c.conexion();
    //instanciamientos (objetos) de clases
    JFR_Principal p=new JFR_Principal();
    
    //Variables
    String clave;
    /* bandera para activar cuando se presione el boton de nuevo*/
    public static boolean nuevo = false;
    
    
    public JIFR_Proveedores() {
        initComponents();
        //damos un tamaño predeterminado, ajustando a la altura del escritorio, y a su propia anchura(la defectto del diseño)
        this.setSize(this.getWidth(),p.Escritorio.getHeight());

        
        txtid.setEditable(false);
        limpiar();
        bloquear();
        mostrar_datos("");
    }
    
    /* limpiamos todos los campos de texto */
    public void limpiar()
    {
        txtid.setText("");
        txtrfc.setText("");
        txtnom.setText("");
        txtemail.setText("");
        txttel.setText("");
        txtfax.setText("");
        txtcd.setText("");
        txtcol.setText("");
        txtedo.setText("");
        txtcalle.setText("");
        txtnum.setText("");
        txtcp.setText("");
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
        txtrfc.setEnabled(false);
        txtnom.setEnabled(false);
        txtemail.setEnabled(false);
        txttel.setEnabled(false);
        txtfax.setEnabled(false);
        txtcd.setEnabled(false);
        txtcol.setEnabled(false);
        txtedo.setEnabled(false);
        txtcalle.setEnabled(false);
        txtnum.setEnabled(false);
        txtcp.setEnabled(false);
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
        txtrfc.setEnabled(true);
        txtnom.setEnabled(true);
        txtemail.setEnabled(true);
        txttel.setEnabled(true);
        txtfax.setEnabled(true);
        txtcd.setEnabled(true);
        txtcol.setEnabled(true);
        txtedo.setEnabled(true);
        txtcalle.setEnabled(true);
        txtnum.setEnabled(true);
        txtcp.setEnabled(true);
    }
    
    void habilitar_txts()
    {
        //txtid.setEnabled(true);
        txtrfc.setEnabled(true);
        txtnom.setEnabled(true);
        txtemail.setEnabled(true);
        txttel.setEnabled(true);
        txtfax.setEnabled(true);
        txtcd.setEnabled(true);
        txtcol.setEnabled(true);
        txtedo.setEnabled(true);
        txtcalle.setEnabled(true);
        txtnum.setEnabled(true);
        txtcp.setEnabled(true);
    }

    //Metodo para OBTENER y GENERAR la clave del servicio
    void obtener_clave()
    {
        //Instanciamos la clase que generara la clave
        gen_clave objgen = new gen_clave(); 
        
        //LLAMAMOS EL METODO CONSULTAR ULTIVA CLAVE GUARDADA EN LA BASE DE DATOS
        //MANDAMOS COMO PARAMETROS EL ATRIBUTO DE LA CLAVE DE LA BASE DE DATOS Y LA TABLA EN CUAL QUEREMOS BUSCAR LA CLAVE
        String ult_codigo = objgen.cons_ult_clave("clave_pr", "proveedores");           
        System.out.println(ult_codigo);
        //verificamos si existen claves en la base de datos, si es null(si no hay claves) nos vamos al else y asignamos una nosotros
        if(ult_codigo != null)
        {        
            ult_codigo = ult_codigo.substring(3,8);//CON EL SUBSTRING SEPARAMOS LA CLAVE PARA QUE SOLO QUEDE EL PURO NUMERO(CLI-00001)
            //UNA VEZ OBTENIDO EL NUMERO LO MANDAMOS A UN METODO PARA QUE INCREMENTE A 1
            clave = objgen.sumar_clave("prv",ult_codigo);
        }
        else
        {
            clave = "prv00001";
        }
    }
    
    void registrar()
    {
        String rfc,nombre, tel, fax, email, ciudad, colonia, calle, num,estado, cp; 
        rfc = txtrfc.getText();
        nombre = txtnom.getText();
        tel = txttel.getText();
        fax = txtfax.getText();
        email = txtemail.getText();
        ciudad = txtcd.getText();
        colonia = txtcol.getText();
        calle = txtcalle.getText();
        num = txtnum.getText();
        estado = txtedo.getText();
        cp = txtcp.getText();
        
        //Procedimiento para insertar datos en la tabla servicio
        String consulta_sql = "insert into proveedores values (?,?,?,?,?,?,?,?,?,?,?,?)";
        //sql = "INSERT INTO clientes (Id_cli,nom_cli,ape_cli,tel_cli,dir_cli,email_cli,rfc_cli,cel_cli,ciu_cli,est_cli,col_cli,cp_cli) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try 
        {
            PreparedStatement psd = con.prepareStatement(consulta_sql);
            psd.setString(1,clave);
            psd.setString (2, rfc);
            psd.setString(3, nombre);
            psd.setString(4, email);
            psd.setString (5, tel);
            psd.setString (6, fax);
            psd.setString (7, estado);
            psd.setString (8, ciudad);
            psd.setString (9, colonia);
            psd.setString (10, calle);
            psd.setString (11, num);
            psd.setString (12, cp);
            int grd = psd.executeUpdate();
            
            //Si se guardo con exito entramos al if
            if(grd>0)
            {
                JOptionPane.showMessageDialog(null, "Proveedor " +  nombre +  " guardado con exito", "Registro Completado", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                bloquear();
            }
            
        } 
        catch (SQLException ex) {
            //Logger.getLogger(JIFR_Servicios.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }
    
    //Metodo para CONSULTAR los datos
    void mostrar_datos(String dato)
    {
        //variable para bloq txtbuscar si no hay registros
        boolean bloq_buscar=true;
        
        //Declaramos los encabezados que tendra la tabla en un arreglo
        String encabezados[] =  {"Clave", "RFC", "Nombre de Empresa", "Email", "Telefono", "Fax", "Estado", "Ciudad", "Colonia", "Calle", "Numero", "CP"};
        String registros[] = new String [12];    //declaramos el arreglo donde se guardaran los registros
     
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
            ResultSet rs = st.executeQuery("select * from proveedores where CONCAT(clave_pr, rfc_pr, nom_pr, correo_pr,tel_pr,"
                    + "fax_pr,edo_pr,cd_pr,colonia_pr,calle_pr,num_pr,cp_pr) like '%"+ dato +"%'");
            
            while(rs.next())
            {
                bloq_buscar = false; //hacemos referencia a no bloq el txtbuscar, ya que hay registros
                //guardamos los registros en nuestro arreglo
                registros[0] = rs.getString("clave_pr");  //decimos que se obtenga el dato del atributo clave_serv
                registros[1] = rs.getString("rfc_pr");
                registros[2] = rs.getString("nom_pr");
                registros[3] = rs.getString("correo_pr");
                registros[4] = rs.getString("tel_pr");
                registros[5] = rs.getString("fax_pr");
                registros[6] = rs.getString("edo_pr");
                registros[7] = rs.getString("cd_pr");
                registros[8] = rs.getString("colonia_pr");
                registros[9] = rs.getString("calle_pr");
                registros[10] = rs.getString("num_pr");
                registros[11] = rs.getString("cp_pr");
                model.addRow(registros);    //añadimos nuestro arreglo al modelo de tabla 
            }
            tbprov.setModel(model);    //le añadimos a NUESTRA TABLA todo el modelo anterior
            
            
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
            Logger.getLogger(JIFR_Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    //metodo para modificar un registro
    void modificar()
    {
       String rfc,nombre, tel, fax, email, ciudad, colonia, calle, num,estado, cp; 
        rfc = txtrfc.getText();
        nombre = txtnom.getText();
        tel = txttel.getText();
        fax = txtfax.getText();
        email = txtemail.getText();
        ciudad = txtcd.getText();
        colonia = txtcol.getText();
        calle = txtcalle.getText();
        num = txtnum.getText();
        estado = txtedo.getText();
        cp = txtcp.getText();
    
        try 
        {
           //consulta de ACTUALIZAR en MySQL
            String consulta_modificar = "UPDATE proveedores SET rfc_pr='"+rfc+"',nom_pr='"+ nombre +"', correo_pr='"+ email 
            +"', tel_pr='"+ tel +"', fax_pr='"+fax+"', edo_pr='"+estado+"', cd_pr='"+ciudad+"', colonia_pr='"+colonia+"'"
            + ", calle_pr='"+calle+"', num_pr='"+num+"', cp_pr='"+cp+"' WHERE clave_pr = '"+ txtid.getText() +"'";

            PreparedStatement psd = con.prepareStatement( consulta_modificar );
            int grd = psd.executeUpdate();  //ejecutamos la consulta
            
            if( grd > 0)    //si se completo la modificacion entra al if
            {
                JOptionPane.showMessageDialog(null, "Proveedor " + txtnom.getText() +  " modificado con exito", "Registro Modificado", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                bloquear();
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(JIFR_Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void eliminar()
    {
        try 
        {
            PreparedStatement psd = con.prepareStatement("DELETE FROM proveedores WHERE clave_pr='"+ txtid.getText() +"'");
            int grd = psd.executeUpdate();  //ejecutamos la consulta
            
            if( grd > 0)    //si se completo la eliminacion entra al if
            {
                JOptionPane.showMessageDialog(null, "Proveedor " + txtnom.getText() +  " eliminado con exito", "Registro Eliminado", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                bloquear();
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(JIFR_Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Ventana = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtrfc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtnom = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txttel = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtfax = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtedo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtcd = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtcol = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtcalle = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtnum = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtcp = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtbuscar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbprov = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btnnuevo = new javax.swing.JButton();
        btnagregar = new javax.swing.JButton();
        btnactualizar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Administrar Proveedores");

        Ventana.setPreferredSize(new java.awt.Dimension(850, 900));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Proveedor"));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Generales", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel1.setText("Id Proveedor:");

        jLabel2.setText("RFC: ");

        txtrfc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrfcActionPerformed(evt);
            }
        });

        jLabel3.setText("Nombre de la Empresa:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(125, 125, 125)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtrfc))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnom)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtrfc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contacto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel4.setText("Email:");

        jLabel5.setText("Telefono:");

        jLabel6.setText("Fax:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfax)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtfax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Direccion", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel7.setText("Estado:");

        jLabel8.setText("Ciudad:");

        jLabel9.setText("Colonia:");

        jLabel10.setText("Calle:");

        jLabel11.setText("Numero:");

        jLabel12.setText("CP:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtedo)
                    .addComponent(txtcalle, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtcd)
                    .addComponent(txtnum, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtcol)
                    .addComponent(txtcp, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtedo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtcd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtcol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtcalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtcp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Proveedores Almacenados"));

        jLabel13.setText("Buscar:");

        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        tbprov.setModel(new javax.swing.table.DefaultTableModel(
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
        tbprov.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbprovMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbprov);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

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

        btneliminar.setText("Eliminar");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        btncancelar.setText("Cancelar");
        btncancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarActionPerformed(evt);
            }
        });

        btnsalir.setText("Salir");
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnactualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btneliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnagregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnnuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnsalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnnuevo)
                .addGap(29, 29, 29)
                .addComponent(btnagregar)
                .addGap(30, 30, 30)
                .addComponent(btnactualizar)
                .addGap(31, 31, 31)
                .addComponent(btneliminar)
                .addGap(30, 30, 30)
                .addComponent(btncancelar)
                .addGap(31, 31, 31)
                .addComponent(btnsalir)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout VentanaLayout = new javax.swing.GroupLayout(Ventana);
        Ventana.setLayout(VentanaLayout);
        VentanaLayout.setHorizontalGroup(
            VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VentanaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(VentanaLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        VentanaLayout.setVerticalGroup(
            VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VentanaLayout.createSequentialGroup()
                .addGroup(VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(VentanaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(VentanaLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(Ventana);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtrfcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrfcActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrfcActionPerformed

    private void btnagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarActionPerformed
        registrar();
        mostrar_datos("");  //cargamos nuestra tabla de mostrar datos
    }//GEN-LAST:event_btnagregarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        nuevo=true;
        desbloquear();
        limpiar();
        obtener_clave();    //obtenemos la clave automaticamente con nuestros metodos
        txtid.setText(clave);  //escribimos la clave en el campo del ID
        txtrfc.requestFocus();  //le damos el foco al campo nombre_servicio
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        nuevo=false;
        bloquear();
        limpiar();
    }//GEN-LAST:event_btncancelarActionPerformed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        mostrar_datos(txtbuscar.getText());
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void tbprovMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbprovMouseClicked
        if( evt.getButton() == 1 )  //si se da click dentro de la tabla entra al if
        {
            if( nuevo == false )    //Si el boton nuevo NO ESTA PRESIONADO entra
            {
                int fila = tbprov.getSelectedRow();    //Guardamos la fila que se esta seleccionando (de la tabla) en una variable

                if (fila >= 0)  //si fila es igual o mayor que 0 quiere decir que una de las filas son seleccionadas
                {
                    //obtenemos el texto de cada una de las columnas y las escibimos en cada campo de texto
                    txtid.setText(tbprov.getValueAt(fila, 0).toString()); //obtenemos el texto del num de fila y columna
                    txtrfc.setText(tbprov.getValueAt(fila,1).toString());
                    txtnom.setText(tbprov.getValueAt(fila, 2).toString());
                    txtemail.setText(tbprov.getValueAt(fila, 3).toString());
                    txttel.setText(tbprov.getValueAt(fila, 4).toString());
                    txtfax.setText(tbprov.getValueAt(fila, 5).toString());
                    txtedo.setText(tbprov.getValueAt(fila, 6).toString());
                    txtcd.setText(tbprov.getValueAt(fila, 7).toString());
                    txtcol.setText(tbprov.getValueAt(fila, 8).toString());
                    txtcalle.setText(tbprov.getValueAt(fila, 9).toString());
                    txtnum.setText(tbprov.getValueAt(fila, 10).toString());
                    txtcp.setText(tbprov.getValueAt(fila, 11).toString());
                    
                    
                    habilitar_txts();   //habilitamos todos los campos de texto para poder sobrescribir en ellos
                    btnactualizar.setEnabled(true);  //habilitamos el boton para poder actualizar
                    btneliminar.setEnabled(true);  //habilitamos el boton para poder eliminar
                }   
            }
        
        }
    }//GEN-LAST:event_tbprovMouseClicked

    private void btnactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizarActionPerformed
        modificar();
        mostrar_datos("");  //cargamos nuestra tabla de mostrar datos
    }//GEN-LAST:event_btnactualizarActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        String nombre = txtnom.getText();
        
        //preguntamos si esta seguro de elimnar, si si entramos al if y eliminamos el registro
        int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro que deseas eliminar el proveedor "+ nombre +"?", "Alerta!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        //si=0    no=1
        if(resp == 0)
        {
            eliminar();
            mostrar_datos("");  //cargamos nuestra tabla de mostrar datos
        }
    }//GEN-LAST:event_btneliminarActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnsalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Ventana;
    private javax.swing.JButton btnactualizar;
    private javax.swing.JButton btnagregar;
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnsalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbprov;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtcalle;
    private javax.swing.JTextField txtcd;
    private javax.swing.JTextField txtcol;
    private javax.swing.JTextField txtcp;
    private javax.swing.JTextField txtedo;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtfax;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnom;
    private javax.swing.JTextField txtnum;
    private javax.swing.JTextField txtrfc;
    private javax.swing.JTextField txttel;
    // End of variables declaration//GEN-END:variables
}
