package Interfaz;

import Conexiones.conectar;
import Clases.GenerarCodigos;
import Clases.gen_clave;
import java.awt.Dimension;

import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.logging.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class JIFR_Clientes extends javax.swing.JInternalFrame {
   
    /* Declaramos la variable para hacer conexion a la DB */
    conectar c = new conectar();
    Connection con = c.conexion();
    //instanciamientos (objetos) de clases
    JFR_Principal p=new JFR_Principal();
    
    //Variables
    String clave;
    /* bandera para activar cuando se presione el boton de nuevo*/
    public static boolean nuevo = false;
    
    public JIFR_Clientes() {
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
        txtappat.setText("");
        txtapmat.setText("");
        txtemail.setText("");
        txttel.setText("");
        txtcel.setText("");
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
        txtappat.setEnabled(false);
        txtemail.setEnabled(false);
        txttel.setEnabled(false);
        txtcel.setEnabled(false);
        txtappat.setEnabled(false);
        txtapmat.setEnabled(false);
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
        txtappat.setEnabled(true);
        txtemail.setEnabled(true);
        txttel.setEnabled(true);
        txtcel.setEnabled(true);
        txtapmat.setEnabled(true);
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
        txtappat.setEnabled(true);
        txtemail.setEnabled(true);
        txttel.setEnabled(true);
        txtcel.setEnabled(true);
        txtappat.setEnabled(true);
        txtapmat.setEnabled(true);
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
        String ult_codigo = objgen.cons_ult_clave("clave_cl", "clientes");           
        System.out.println(ult_codigo);
        //verificamos si existen claves en la base de datos, si es null(si no hay claves) nos vamos al else y asignamos una nosotros
        if(ult_codigo != null)
        {        
            ult_codigo = ult_codigo.substring(3,8);//CON EL SUBSTRING SEPARAMOS LA CLAVE PARA QUE SOLO QUEDE EL PURO NUMERO(CLI-00001)
            //UNA VEZ OBTENIDO EL NUMERO LO MANDAMOS A UN METODO PARA QUE INCREMENTE A 1
            clave = objgen.sumar_clave("cli",ult_codigo);
        }
        else
        {
            clave = "cli00001";
        }
    }
    
    void registrar()
    {
        String rfc,nombres, ap_paterno,ap_materno, tel, cel, email, ciudad, colonia, calle, num,estado, cp; 
        rfc = txtrfc.getText();
        nombres = txtnom.getText();
        ap_paterno = txtappat.getText();
        ap_materno = txtapmat.getText();
        tel = txttel.getText();
        cel = txtcel.getText();
        email = txtemail.getText();
        ciudad = txtcd.getText();
        colonia = txtcol.getText();
        calle = txtcalle.getText();
        num = txtnum.getText();
        estado = txtedo.getText();
        cp = txtcp.getText();
        
        //Procedimiento para insertar datos en la tabla servicio
        String consulta_sql = "insert into clientes values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        //sql = "INSERT INTO clientes (Id_cli,nom_cli,ape_cli,tel_cli,dir_cli,email_cli,rfc_cli,cel_cli,ciu_cli,est_cli,col_cli,cp_cli) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try 
        {
            PreparedStatement psd = con.prepareStatement(consulta_sql);
            psd.setString(1,clave);
            psd.setString (2, rfc);
            psd.setString(3, nombres);
            psd.setString(4, ap_paterno);
            psd.setString(5, ap_materno);
            psd.setString (6, calle);
            psd.setString (7, num);
            psd.setString (8, colonia);
            psd.setString (9, cp);
            psd.setString (10, ciudad);
            psd.setString (11, estado);
            psd.setString (12, tel);
            psd.setString (13, cel);
            psd.setString (14, email);
            int grd = psd.executeUpdate();
            
            //Si se guardo con exito entramos al if
            if(grd>0)
            {
                JOptionPane.showMessageDialog(null, "Cliente " +  nombres +  " guardado con exito", "Registro Completado", JOptionPane.INFORMATION_MESSAGE);
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
        String encabezados[] =  {"Clave", "Apellido Paterno", "Apellido Materno", "Nombre", "RFC", "Telefono", "Celular", "Correo", "Calle", "Numero", "Colonia", "Ciudad", "Estado", "CP"  };
        String registros[] = new String [14];    //declaramos el arreglo donde se guardaran los registros
     
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
            ResultSet rs = st.executeQuery("select * from clientes where CONCAT(clave_cl, RFC_cl, nom_cl, ApPat_cl, ApMat_cl,calleD_cl,"
                    + "numD_cl,colD_cl,cpD_cl,cdD_cl,estado_cl,tel_cl,cel_cl,correo_cl) like '%"+ dato +"%'");
            
            while(rs.next())
            {
                bloq_buscar = false; //hacemos referencia a no bloq el txtbuscar, ya que hay registros
                //guardamos los registros en nuestro arreglo
                registros[0] = rs.getString("clave_cl");  //decimos que se obtenga el dato del atributo clave_serv
                registros[1] = rs.getString("ApPat_cl");
                registros[2] = rs.getString("ApMat_cl");
                registros[3] = rs.getString("nom_cl");
                registros[4] = rs.getString("RFC_cl");
                registros[5] = rs.getString("tel_cl");
                registros[6] = rs.getString("cel_cl");
                registros[7] = rs.getString("correo_cl");
                registros[8] = rs.getString("calleD_cl");
                registros[9] = rs.getString("numD_cl");
                registros[10] = rs.getString("colD_cl");
                registros[11] = rs.getString("cdD_cl");
                registros[12] = rs.getString("estado_cl");
                registros[13] = rs.getString("cpD_cl");
                model.addRow(registros);    //añadimos nuestro arreglo al modelo de tabla 
            }
            tbclientes.setModel(model);    //le añadimos a NUESTRA TABLA todo el modelo anterior
            
            
            if(bloq_buscar == false)
            {
                //Si hay algun registro habilitamos el txt de buscar
                txtbuscar.setEnabled(true);
            }
            else
            {
                //si no hay ningun registro desabilitamos el txt de buscar
                txtbuscar.setEnabled(false);
            }
            
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
                
    }
    
    //metodo para modificar un registro
    void modificar()
    {
        String rfc,nombres, ap_pat, ap_mat, tel, cel, email, ciudad, colonia, calle, num,estado, cp; 
        rfc = txtrfc.getText();
        nombres = txtnom.getText();
        ap_pat = txtappat.getText();
        ap_mat = txtapmat.getText();
        tel = txttel.getText();
        cel = txtcel.getText();
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
            String consulta_modificar = "UPDATE clientes SET RFC_cl='"+rfc+"',nom_cl='"+ nombres +"', ApPat_cl='"+ ap_pat 
            +"', ApMat_cl='"+ ap_mat +"', calleD_cl='"+ calle +"', numD_cl='"+num+"', colD_cl='"+colonia+"', cpD_cl='"+cp+"', cdD_cl='"+ciudad+"'"
            + ", estado_cl='"+estado+"', tel_cl='"+tel+"', cel_cl='"+cel+"', correo_cl='"+email+"' WHERE clave_cl = '"+ txtid.getText() +"'";

            PreparedStatement psd = con.prepareStatement( consulta_modificar );
            int grd = psd.executeUpdate();  //ejecutamos la consulta
            
            if( grd > 0)    //si se completo la modificacion entra al if
            {
                JOptionPane.showMessageDialog(null, "Cliente " + txtnom.getText() +  " modificado con exito", "Registro Modificado", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                bloquear();
            }
        } 
        catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }
    
    void eliminar()
    {
        try 
        {
            PreparedStatement psd = con.prepareStatement("DELETE FROM clientes WHERE clave_cl='"+ txtid.getText() +"'");
            int grd = psd.executeUpdate();  //ejecutamos la consulta
            
            if( grd > 0)    //si se completo la eliminacion entra al if
            {
                JOptionPane.showMessageDialog(null, "Cliente " + txtnom.getText() +  " eliminado con exito", "Registro Eliminado", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                bloquear();
            }
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }
  
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        mnmodificar = new javax.swing.JMenuItem();
        mneliminar = new javax.swing.JMenuItem();
        desplazamiento = new javax.swing.JScrollPane();
        Ventana = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnnuevo = new javax.swing.JButton();
        btnagregar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        txtbuscar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtrfc = new javax.swing.JTextField();
        txtnom = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtappat = new javax.swing.JTextField();
        txtapmat = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txttel = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtcel = new javax.swing.JTextField();
        txtemail = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnactualizar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbclientes = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        txtcalle = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtcd = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtcol = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtedo = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtcp = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtnum = new javax.swing.JTextField();
        btnsalir = new javax.swing.JButton();

        mnmodificar.setText("Modificar");
        mnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnmodificarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mnmodificar);

        mneliminar.setText("Eliminar");
        mneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mneliminarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mneliminar);

        setClosable(true);
        setIconifiable(true);
        setTitle("REGISTRO DE CLIENTES");

        Ventana.setPreferredSize(new Dimension(this.getWidth(),900));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registrar", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

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

        btncancelar.setText("Cancelar");
        btncancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnagregar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnnuevo)
                .addGap(34, 34, 34)
                .addComponent(btnagregar)
                .addGap(32, 32, 32)
                .addComponent(btncancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtbuscar.setToolTipText("Seleccione registro");
        txtbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarActionPerformed(evt);
            }
        });
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        jLabel10.setText("Buscar:");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Cliente"));
        jPanel3.setToolTipText("Datos del Cliente");

        jLabel1.setText("ID Cliente:");

        jLabel9.setText("RFC:");

        txtrfc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrfcActionPerformed(evt);
            }
        });
        txtrfc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtrfcKeyTyped(evt);
            }
        });

        txtnom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnomKeyTyped(evt);
            }
        });

        jLabel2.setText("Nombres:");

        jLabel3.setText("Apellido Paterno:");

        txtappat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtappatKeyTyped(evt);
            }
        });

        jLabel8.setText("Apellido Materno:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(379, 379, 379)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtrfc, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtappat, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtapmat, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtrfc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtappat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtapmat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Contacto"));

        jLabel7.setText("Email:");

        jLabel6.setText("Telefono:");

        txttel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttelActionPerformed(evt);
            }
        });
        txttel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txttelKeyTyped(evt);
            }
        });

        jLabel4.setText("Celular:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcel, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(txtcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modificar", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnactualizar))
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnactualizar)
                .addGap(26, 26, 26)
                .addComponent(btneliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbclientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tbclientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbclientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbclientes);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Direccion del Cliente"));

        jLabel27.setText("Calle:");

        jLabel28.setText("Ciudad:");

        jLabel29.setText("Colonia:");

        jLabel30.setText("Estado:");

        jLabel31.setText("CP:");

        jLabel32.setText("Numero: ");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtcd, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txtcalle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnum, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcol, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtcp, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txtedo))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel29)
                        .addComponent(txtcol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28)
                        .addComponent(txtcd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel30)
                        .addComponent(txtedo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel32)
                        .addComponent(txtnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel27)
                        .addComponent(txtcalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel31)
                        .addComponent(txtcp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnsalir.setText("Salir");
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout VentanaLayout = new javax.swing.GroupLayout(Ventana);
        Ventana.setLayout(VentanaLayout);
        VentanaLayout.setHorizontalGroup(
            VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VentanaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(VentanaLayout.createSequentialGroup()
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(VentanaLayout.createSequentialGroup()
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(33, 33, 33)
                            .addGroup(VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                    .addComponent(btnsalir, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(VentanaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(226, Short.MAX_VALUE)))
        );
        VentanaLayout.setVerticalGroup(
            VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, VentanaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(VentanaLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnsalir, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(VentanaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(745, Short.MAX_VALUE)))
        );

        desplazamiento.setViewportView(Ventana);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desplazamiento, javax.swing.GroupLayout.DEFAULT_SIZE, 1175, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desplazamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 141, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizarActionPerformed
        modificar();
        mostrar_datos("");  //cargamos nuestra tabla de mostrar datos
}//GEN-LAST:event_btnactualizarActionPerformed

private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
    this.dispose();
}//GEN-LAST:event_btnsalirActionPerformed

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

private void btnagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarActionPerformed
   registrar();
   mostrar_datos("");  //cargamos nuestra tabla de mostrar datos
}//GEN-LAST:event_btnagregarActionPerformed

private void txtbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarActionPerformed

}//GEN-LAST:event_txtbuscarActionPerformed

private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
    mostrar_datos(txtbuscar.getText());
}//GEN-LAST:event_txtbuscarKeyReleased

private void txttelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelKeyTyped

}//GEN-LAST:event_txttelKeyTyped

private void txtrfcKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrfcKeyTyped

}//GEN-LAST:event_txtrfcKeyTyped

private void txtnomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnomKeyTyped

}//GEN-LAST:event_txtnomKeyTyped

private void mneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mneliminarActionPerformed

}//GEN-LAST:event_mneliminarActionPerformed

private void mnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnmodificarActionPerformed

}//GEN-LAST:event_mnmodificarActionPerformed

    private void txtrfcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrfcActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrfcActionPerformed

    private void txttelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttelActionPerformed

    private void tbclientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbclientesMouseClicked
        if( evt.getButton() == 1 )  //si se da click dentro de la tabla entra al if
        {
            if( nuevo == false )    //Si el boton nuevo NO ESTA PRESIONADO entra
            {
                int fila = tbclientes.getSelectedRow();    //Guardamos la fila que se esta seleccionando (de la tabla) en una variable

                if (fila >= 0)  //si fila es igual o mayor que 0 quiere decir que una de las filas son seleccionadas
                {
                    //obtenemos el texto de cada una de las columnas y las escibimos en cada campo de texto
                    txtid.setText(tbclientes.getValueAt(fila, 0).toString()); //obtenemos el texto del num de fila y columna
                    txtappat.setText(tbclientes.getValueAt(fila,1).toString());
                    txtapmat.setText(tbclientes.getValueAt(fila,2).toString());
                    txtnom.setText(tbclientes.getValueAt(fila, 3).toString());
                    txtrfc.setText(tbclientes.getValueAt(fila, 4).toString());
                    txttel.setText(tbclientes.getValueAt(fila, 5).toString());
                    txtcel.setText(tbclientes.getValueAt(fila, 6).toString());
                    txtemail.setText(tbclientes.getValueAt(fila, 7).toString());
                    txtcalle.setText(tbclientes.getValueAt(fila, 8).toString());
                    txtnum.setText(tbclientes.getValueAt(fila, 9).toString());
                    txtcol.setText(tbclientes.getValueAt(fila, 10).toString());
                    txtcd.setText(tbclientes.getValueAt(fila, 11).toString());
                    txtedo.setText(tbclientes.getValueAt(fila, 12).toString());
                    txtcp.setText(tbclientes.getValueAt(fila, 13).toString());
                    
                    
                    habilitar_txts();   //habilitamos todos los campos de texto para poder sobrescribir en ellos
                    btnactualizar.setEnabled(true);  //habilitamos el boton para poder actualizar
                    btneliminar.setEnabled(true);  //habilitamos el boton para poder eliminar
                }   
            }
        
        }
    }//GEN-LAST:event_tbclientesMouseClicked

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        String nombre = txtnom.getText();
        
        //preguntamos si esta seguro de elimnar, si si entramos al if y eliminamos el registro
        int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro que deseas eliminar el cliente "+ nombre +"?", "Alerta!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        //si=0    no=1
        if(resp == 0)
        {
            eliminar();
            mostrar_datos("");  //cargamos nuestra tabla de mostrar datos
        }
    }//GEN-LAST:event_btneliminarActionPerformed

    private void txtappatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtappatKeyTyped

    }//GEN-LAST:event_txtappatKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Ventana;
    private javax.swing.JButton btnactualizar;
    private javax.swing.JButton btnagregar;
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnsalir;
    private javax.swing.JScrollPane desplazamiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem mneliminar;
    private javax.swing.JMenuItem mnmodificar;
    private javax.swing.JTable tbclientes;
    private javax.swing.JTextField txtapmat;
    private javax.swing.JTextField txtappat;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtcalle;
    private javax.swing.JTextField txtcd;
    private javax.swing.JTextField txtcel;
    private javax.swing.JTextField txtcol;
    private javax.swing.JTextField txtcp;
    private javax.swing.JTextField txtedo;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnom;
    private javax.swing.JTextField txtnum;
    private javax.swing.JTextField txtrfc;
    private javax.swing.JTextField txttel;
    // End of variables declaration//GEN-END:variables
    conectar cc = new conectar();
    Connection cn = cc.conexion();
}
