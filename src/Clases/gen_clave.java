
package Clases;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import Conexiones.conectar;

public class gen_clave {
    
    conectar c = new conectar();
    Connection con = c.conexion();

    //METODO PARA CONSULTAR LA ULTIMA CLAVE, RECIBIENDO COMO PARAMETROS EL ATRIBUTO DE LA CLAVE PRIMARIA Y EL NOMBRE DE LA TABLA A BUSCAR
    public String cons_ult_clave(String clave, String tabla)
    {   
        String ult_clave=null;
                
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select " + clave + " from " + tabla);
            
            while( rs.next() )
            {
                //SE VAN A IR GUARDANDO TODAS LAS CLAVES HASTA LLEGAR A LA ULTIMA
                ult_clave = rs.getString(1);
            }
            
//            System.out.println(ult_clave);
            
        } catch (SQLException ex) {
            Logger.getLogger(gen_clave.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ult_clave;
    }
    
    
    //METODO PARA INCREMENTAR A 1 LA CLAVE RECIENTEMENTE OBTENIDA
    //RECIBIMOS COMO PARAMETROS LA ABREBIACION DE LA TABLA (EJ: CLI, EMP, PROD) Y EL NUMERO DE LA CLAVE
    public String sumar_clave(String tabla, String codigo)
    {   
        //convertimos el numero de la clave a entero y lo incrementamos 1
        int num = Integer.parseInt(codigo);
        num++;
        //mandamos ese numero incrementado y la abrebiacion d ela tabla para q rellene con 0
        String clave = agregar_0(tabla,num);
        return clave;
    }
    
    //METODO PARA RELLENAR CON 0 LA CLAVE (EJ: CLI00001)
    //RECIBIMOS COMO PARAMETROS LA ABREBIACION DE LA TABLA (EJ: CLI, EMP, PROD) Y EL NUMERO DE LA CLAVE YA INCREMENTADO
    String agregar_0(String tabla , int num)
    {
        String clave = tabla;   //TABLA = CLI, EMP, PROD
        
        //CONVERTIMOS EL NUMERO INCREMETADO EN CADENA
        String cad_num = Integer.toString(num);
        int longitud = cad_num.length();    //SACAMOS LA LONGITUD DEL NUMERO INCREMENTADO
        longitud = longitud + 3;    //SUMAMOS LA LONGITUD DEL NUMERO INCREMETADO MAS 3 (3 POR QUE ES LA LONGITUD DE LA ABREBIACIONES DE LAS TABLAS)
        
        for(int x = longitud; x<8; x++)
        {
            clave = clave + "0";    //VAMOS CONCATENANDO LOS 0 A LA CLAVE
        }
        
        clave = clave + num;    //DESÑPUES DE CONCATENAR TODOS LOS 0 LE CONCATENAMOS EL NUMERO INCREMENTADO (EJ CLI00001)
        return clave;
    }
    
}
