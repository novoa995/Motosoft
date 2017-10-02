package Interfaz;

import Clases.gen_clave;
import Conexiones.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class JIFR_Empleados extends javax.swing.JInternalFrame {
    
    /* Declaramos la variable para hacer conexion a la DB */
    conectar c = new conectar();
    Connection con = c.conexion();
    //instanciamientos (objetos) de clases
    JFR_Principal p=new JFR_Principal();
    
    //Variables
    String clave;
    /* bandera para activar cuando se presione el boton de nuevo*/
    public static boolean nuevo = false;

    public JIFR_Empleados() {
        initComponents();
        
        //damos un tamaño predeterminado, ajustando a la altura del escritorio, y a su propia anchura(la defectto del diseño)
        this.setSize(this.getWidth(),p.Escritorio.getHeight());
        
        txtid.setEditable(false);
        limpiar();
        bloquear();
        mostrar_datos("");
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
        txtfecha.setDate(StringADate(""));
        txtpuesto.setText("");
        txtsueldo.setText("");
        txthorent.setText("");
        txthorsal.setText("");
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
        txtnom.setEnabled(false);
        txtappat.setEnabled(false);
        txtapmat.setEnabled(false);
        txtemail.setEnabled(false);
        txttel.setEnabled(false);
        txtcel.setEnabled(false);
        txtcd.setEnabled(false);
        txtcol.setEnabled(false);
        txtedo.setEnabled(false);
        txtcalle.setEnabled(false);
        txtnum.setEnabled(false);
        txtcp.setEnabled(false);
        txtfecha.setEnabled(false);
        txtpuesto.setEnabled(false);
        txtsueldo.setEnabled(false);
        txthorent.setEnabled(false);
        txthorsal.setEnabled(false);
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
        txtnom.setEnabled(true);
        txtappat.setEnabled(true);
        txtapmat.setEnabled(true);
        txtemail.setEnabled(true);
        txttel.setEnabled(true);
        txtcel.setEnabled(true);
        txtcd.setEnabled(true);
        txtcol.setEnabled(true);
        txtedo.setEnabled(true);
        txtcalle.setEnabled(true);
        txtnum.setEnabled(true);
        txtcp.setEnabled(true);
        txtfecha.setEnabled(true);
        txtpuesto.setEnabled(true);
        txtsueldo.setEnabled(true);
        txthorent.setEnabled(true);
        txthorsal.setEnabled(true);
    }
    
    void habilitar_txts()
    {
        txtid.setEnabled(true);
        txtnom.setEnabled(true);
        txtappat.setEnabled(true);
        txtapmat.setEnabled(true);
        txtemail.setEnabled(true);
        txttel.setEnabled(true);
        txtcel.setEnabled(true);
        txtcd.setEnabled(true);
        txtcol.setEnabled(true);
        txtedo.setEnabled(true);
        txtcalle.setEnabled(true);
        txtnum.setEnabled(true);
        txtcp.setEnabled(true);
        txtfecha.setEnabled(true);
        txtpuesto.setEnabled(true);
        txtsueldo.setEnabled(true);
        txthorent.setEnabled(true);
        txthorsal.setEnabled(true);
    }
    
    
    //Metodo para CONSULTAR los datos
    void mostrar_datos(String dato)
    {
        //variable para bloq txtbuscar si no hay registros
        boolean bloq_buscar=true;
        
        //Declaramos los encabezados que tendra la tabla en un arreglo
        String encabezados[] =  {"Id Empleado", "Apellido Paterno", "Apellido Materno", "Nombre", "Calle", "Numero", "Colonia", "CP", "Ciudad", "Estado", "Telefono", "Celular",
        "Correo", "Fecha de Ingreso", "Puesto", "Sueldo", "Horario Entrada","Horario Salida"};
        String registros[] = new String [18];    //declaramos el arreglo donde se guardaran los registros
     
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
            ResultSet rs = st.executeQuery("select * from empleados where CONCAT(clave_emp, appat_emp, apmat_emp, nom_emp, calleD_emp, numD_emp,"
                    + "colD_emp,cpD_emp,cdD_emp, edo_emp, tel_emp, cel_emp, correo_emp, ingr_emp, puesto, sueldo, horent_emp, horsal_emp) like '%"+ dato +"%'");
            
            while(rs.next())
            {
                bloq_buscar = false; //hacemos referencia a no bloq el txtbuscar, ya que hay registros
                //guardamos los registros en nuestro arreglo
                registros[0] = rs.getString("clave_emp");  //decimos que se obtenga el dato del atributo clave_serv
                registros[1] = rs.getString("appat_emp");
                registros[2] = rs.getString("apmat_emp");
                registros[3] = rs.getString("nom_emp");
                registros[4] = rs.getString("calleD_emp");
                registros[5] = rs.getString("numD_emp");
                registros[6] = rs.getString("colD_emp");
                registros[7] = rs.getString("cpD_emp");
                registros[8] = rs.getString("cdD_emp");
                registros[9] = rs.getString("edo_emp");
                registros[10] = rs.getString("tel_emp");
                registros[11] = rs.getString("cel_emp");
                registros[12] = rs.getString("correo_emp");
                registros[13] = rs.getString("ingr_emp");
                registros[14] = rs.getString("puesto");
                registros[15] = rs.getString("sueldo");
                registros[16] = rs.getString("horent_emp");
                registros[17] = rs.getString("horsal_emp");
                
                
                model.addRow(registros);    //añadimos nuestro arreglo al modelo de tabla 
            }
            tbemp.setModel(model);    //le añadimos a NUESTRA TABLA todo el modelo anterior
            
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
        String ult_codigo = objgen.cons_ult_clave("clave_emp", "empleados");           
        System.out.println(ult_codigo);
        //verificamos si existen claves en la base de datos, si es null(si no hay claves) nos vamos al else y asignamos una nosotros
        if(ult_codigo != null)
        {        
            ult_codigo = ult_codigo.substring(3,8);//CON EL SUBSTRING SEPARAMOS LA CLAVE PARA QUE SOLO QUEDE EL PURO NUMERO(CLI-00001)
            //UNA VEZ OBTENIDO EL NUMERO LO MANDAMOS A UN METODO PARA QUE INCREMENTE A 1
            clave = objgen.sumar_clave("emp",ult_codigo);
        }
        else
        {
            clave = "emp00001";
        }
    }
    
    void registrar()
    {
        String nom, appat, apmat, email, tel, cel, cd, col, edo,calle, num, cp, fecha, puesto,sueldo, horent, horsal; 
        nom = txtnom.getText();
        appat = txtappat.getText();
        apmat = txtapmat.getText();
        email=txtemail.getText();
        tel=txttel.getText();
        cel=txtcel.getText();
        cd=txtcd.getText();
        col=txtcol.getText();
        edo=txtedo.getText();
        calle=txtcalle.getText();
        num=txtnum.getText();
        cp=txtcp.getText();
        fecha=obt_fecha();
        puesto=txtpuesto.getText();
        sueldo=txtsueldo.getText();
        horent=txthorent.getText();
        horsal=txthorsal.getText();
        
        //Procedimiento para insertar datos en la tabla servicio
        String consulta_sql = "insert into empleados values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        //sql = "INSERT INTO clientes (Id_cli,nom_cli,ape_cli,tel_cli,dir_cli,email_cli,rfc_cli,cel_cli,ciu_cli,est_cli,col_cli,cp_cli) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try 
        {
            PreparedStatement psd = con.prepareStatement(consulta_sql);
            psd.setString(1,clave);
            psd.setString (2, appat);
            psd.setString (3, apmat);
            psd.setString(4, nom);
            psd.setString(5, calle);
            psd.setString (6, num);
            psd.setString (7, col);
            psd.setString (8, cp);
            psd.setString (9, cd);
            psd.setString (10, edo);
            psd.setString (11, tel);
            psd.setString (12, cel);
            psd.setString (13, email);
            psd.setString (14, fecha);
            psd.setString (15, puesto);
            psd.setString (16, sueldo);
            psd.setString (17, horent);
            psd.setString (18, horsal);
            int grd = psd.executeUpdate();
            
            //Si se guardo con exito entramos al if
            if(grd>0)
            {
                JOptionPane.showMessageDialog(null, "Empleado " +  nom +  " guardado con exito", "Registro Completado", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
                bloquear();
            }
            
        } 
        catch (SQLException ex) {
            //Logger.getLogger(JIFR_Servicios.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    
    //metodo para modificar un registro
    void modificar()
    {
        String nom, appat, apmat, email, tel, cel, cd, col, edo,calle, num, cp, fecha, puesto,sueldo, horent, horsal; 
        nom = txtnom.getText();
        appat = txtappat.getText();
        apmat = txtapmat.getText();
        email=txtemail.getText();
        tel=txttel.getText();
        cel=txtcel.getText();
        cd=txtcd.getText();
        col=txtcol.getText();
        edo=txtedo.getText();
        calle=txtcalle.getText();
        num=txtnum.getText();
        cp=txtcp.getText();
        fecha=obt_fecha();
        puesto=txtpuesto.getText();
        sueldo=txtsueldo.getText();
        horent=txthorent.getText();
        horsal=txthorsal.getText();
    
        try 
        {
           //consulta de ACTUALIZAR en MySQL
            String consulta_modificar = "UPDATE empleados SET appat_emp='"+appat+"',apmat_emp='"+apmat+"',nom_emp='"+nom+"', calleD_emp='"+ calle
            +"', numD_emp='"+num+"', colD_emp='"+col+"', cpD_emp='"+cp+"', cdD_emp='"+cd+"', edo_emp='"+edo+"', tel_emp='"+tel
            +"', cel_emp='"+cel+"', correo_emp='"+email+"', ingr_emp='"+fecha+"', puesto='"+puesto+"', sueldo='"+sueldo+"', horent_emp='"+horent+"', horsal_emp='"+horsal+"'"
            + " WHERE clave_emp = '"+ txtid.getText() +"'";

            PreparedStatement psd = con.prepareStatement( consulta_modificar );
            int grd = psd.executeUpdate();  //ejecutamos la consulta
            
            if( grd > 0)    //si se completo la modificacion entra al if
            {
                JOptionPane.showMessageDialog(null, "Empleado " + txtnom.getText() +  " modificado con exito", "Registro Modificado", JOptionPane.INFORMATION_MESSAGE);
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
            PreparedStatement psd = con.prepareStatement("DELETE FROM empleados WHERE clave_emp='"+ txtid.getText() +"'");
            int grd = psd.executeUpdate();  //ejecutamos la consulta
            
            if( grd > 0)    //si se completo la eliminacion entra al if
            {
                JOptionPane.showMessageDialog(null, "Empleado " + txtnom.getText() +  " eliminado con exito", "Registro Eliminado", JOptionPane.INFORMATION_MESSAGE);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        Ventana = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtnom = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtappat = new javax.swing.JTextField();
        txtapmat = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtcalle = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtnum = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtcol = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtcp = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtcd = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtedo = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txttel = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtcel = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtfecha = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        txtpuesto = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txthorent = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtsueldo = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txthorsal = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        btnnuevo = new javax.swing.JButton();
        btnagregar = new javax.swing.JButton();
        btnactualizar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtbuscar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbemp = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setTitle("Administrar Empleados");

        Ventana.setPreferredSize(new java.awt.Dimension(900, 1035));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de los Empleados"));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Generales", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel1.setText("Id de Empleado:");

        jLabel2.setText("Nombre/s:");

        jLabel3.setText("Apellido Paterno:");

        jLabel18.setText("Apellido Materno:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtappat)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtapmat, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txtnom))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtappat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtapmat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Direccion", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel4.setText("Calle:");

        jLabel5.setText("Numero:");

        jLabel6.setText("Colonia:");

        jLabel7.setText("CP:");

        jLabel8.setText("Ciudad:");

        jLabel9.setText("Estado:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtcalle)
                    .addComponent(txtcp, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtnum)
                    .addComponent(txtcd, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtcol)
                    .addComponent(txtedo, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtcalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtcol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtcp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtcd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtedo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contacto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel10.setText("Correo:");

        jLabel11.setText("Telefono:");

        jLabel12.setText("Celular:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtemail)
                    .addComponent(txttel, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Trabajo", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel13.setText("Fecha de Ingreso:");

        jLabel14.setText("Puesto:");

        jLabel15.setText("Horario de Entrada:");

        jLabel16.setText("Sueldo:");

        jLabel19.setText("Horario de Salida:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txthorent, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txthorsal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtsueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txthorent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txthorsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtsueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Empleados Registrados"));

        jLabel17.setText("Buscar:");

        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        tbemp.setModel(new javax.swing.table.DefaultTableModel(
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
        tbemp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbempMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbemp);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout VentanaLayout = new javax.swing.GroupLayout(Ventana);
        Ventana.setLayout(VentanaLayout);
        VentanaLayout.setHorizontalGroup(
            VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VentanaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(VentanaLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        VentanaLayout.setVerticalGroup(
            VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VentanaLayout.createSequentialGroup()
                .addGroup(VentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(VentanaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(VentanaLayout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(Ventana);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 974, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        nuevo=true;
        desbloquear();
        limpiar();
        obtener_clave();    //obtenemos la clave automaticamente con nuestros metodos
        txtid.setText(clave);  //escribimos la clave en el campo del ID
        txtnom.requestFocus();  //le damos el foco al campo nombre_servicio
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btnagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarActionPerformed
        registrar();
        mostrar_datos("");  //cargamos nuestra tabla de mostrar datos
    }//GEN-LAST:event_btnagregarActionPerformed

    private void btnactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizarActionPerformed
        modificar();
        mostrar_datos("");  //cargamos nuestra tabla de mostrar datos
    }//GEN-LAST:event_btnactualizarActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        String nombre = txtnom.getText();

        //preguntamos si esta seguro de elimnar, si si entramos al if y eliminamos el registro
        int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro que deseas eliminar el empleado: "+ nombre +"?", "Alerta!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        //si=0    no=1
        if(resp == 0)
        {
            eliminar();
            mostrar_datos("");  //cargamos nuestra tabla de mostrar datos
        }
    }//GEN-LAST:event_btneliminarActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        nuevo=false;
        bloquear();
        limpiar();
    }//GEN-LAST:event_btncancelarActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnsalirActionPerformed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        mostrar_datos(txtbuscar.getText());
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void tbempMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbempMouseClicked
        if( evt.getButton() == 1 )  //si se da click dentro de la tabla entra al if
        {
            if( nuevo == false )    //Si el boton nuevo NO ESTA PRESIONADO entra
            {
                int fila = tbemp.getSelectedRow();    //Guardamos la fila que se esta seleccionando (de la tabla) en una variable

                if (fila >= 0)  //si fila es igual o mayor que 0 quiere decir que una de las filas son seleccionadas
                {
                    //obtenemos el texto de cada una de las columnas y las escibimos en cada campo de texto
                    txtid.setText(tbemp.getValueAt(fila, 0).toString()); //obtenemos el texto del num de fila y columna
                    txtappat.setText(tbemp.getValueAt(fila,1).toString());
                    txtapmat.setText(tbemp.getValueAt(fila,2).toString());
                    txtnom.setText(tbemp.getValueAt(fila, 3).toString());
                    txtcalle.setText(tbemp.getValueAt(fila, 4).toString());
                    txtnum.setText(tbemp.getValueAt(fila, 5).toString());
                    txtcol.setText(tbemp.getValueAt(fila, 6).toString());
                    txtcp.setText(tbemp.getValueAt(fila, 7).toString());
                    txtcd.setText(tbemp.getValueAt(fila, 8).toString());
                    txtedo.setText(tbemp.getValueAt(fila, 9).toString());
                    txttel.setText(tbemp.getValueAt(fila, 10).toString());
                    txtcel.setText(tbemp.getValueAt(fila, 11).toString());
                    txtemail.setText(tbemp.getValueAt(fila, 12).toString());
                    //txtfecha.setText(tbmotos.getValueAt(fila, 14).toString());
                    txtfecha.setDate(StringADate(tbemp.getValueAt(fila, 13).toString()));
                    txtpuesto.setText(tbemp.getValueAt(fila, 14).toString());
                    txtsueldo.setText(tbemp.getValueAt(fila, 15).toString());
                    txthorent.setText(tbemp.getValueAt(fila, 16).toString());
                    txthorsal.setText(tbemp.getValueAt(fila, 17).toString());
                    
                    habilitar_txts();   //habilitamos todos los campos de texto para poder sobrescribir en ellos
                    btnactualizar.setEnabled(true);  //habilitamos el boton para poder actualizar
                    btneliminar.setEnabled(true);  //habilitamos el boton para poder eliminar
                }   
            }
        
        }
    }//GEN-LAST:event_tbempMouseClicked


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
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbemp;
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
    private com.toedter.calendar.JDateChooser txtfecha;
    private javax.swing.JTextField txthorent;
    private javax.swing.JTextField txthorsal;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnom;
    private javax.swing.JTextField txtnum;
    private javax.swing.JTextField txtpuesto;
    private javax.swing.JTextField txtsueldo;
    private javax.swing.JTextField txttel;
    // End of variables declaration//GEN-END:variables
}
