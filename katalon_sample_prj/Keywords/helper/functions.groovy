package helper
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import java.text.DecimalFormat
import com.ibm.db2.jcc.DB2Driver
import com.kms.katalon.core.logging.KeywordLogger
import com.opencsv.*;
import java.io.FileWriter.*;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.eclipse.persistence.platform.database.DB2Platform
import org.junit.After
import org.eclipse.persistence.platform.database.DB2MainframePlatform
import java.sql.SQLException

public class functions {

	private static Connection connection ;
	private static Connection connection2 ;
	private static double balance_origen ;
	private static double balance_despues ;
	private static  double balance_destino ;
	public static final String DATABASE_USER = "user";
	public static final String DATABASE_PASSWORD = "password";
	public static final String DEFERPREPARES = "false"
	private static DecimalFormat df = new DecimalFormat(".00")
	KeywordLogger log = new KeywordLogger()

	@Keyword
	def connectDB(String url, String dbname, String port, String username, String password){
		String jdbcClassName ="com.ibm.db2.jcc.DB2Driver"
		String conn = "jdbc:db2://" + url + ":" + port + "/" + dbname
		Class.forName(jdbcClassName)

		java.util.Properties connProperties = new java.util.Properties();
		connProperties.put(DATABASE_USER, username);
		connProperties.put(DATABASE_PASSWORD, password);
		connProperties.put(DEFERPREPARES, 'false');

		if(connection != null && !connection.isClosed()){
			connection.close()
		}
		connection = DriverManager.getConnection(conn, connProperties)
		return connection
	}

	@Keyword
	def connectDBSql(String url, String dbname, String port, String username, String password){
		String jdbcClassName ="com.microsoft.sqlserver.jdbc.SQLServerDriver"
		String conn2 = "jdbc:sqlserver://" + url + ":" + port + ";databaseName=" + dbname
		Class.forName(jdbcClassName)
		if(connection2 != null && !connection2.isClosed()){
			connection2.close()
		}
		connection2 = DriverManager.getConnection(conn2, username, password)
		return connection2
	}

	@Keyword
	def consultaBalance(String origen) {
		Statement stm = connection.createStatement()
		ResultSet rs = stm.executeQuery("SELECT dmcbal FROM BDRQCD.TAP00201 WHERE ACCOUNT_NBR = ('"+ origen +"')")

		rs.next()
		String balance = rs.getString("DMCBAL")
		double monto = Double.parseDouble(balance)

		//formateador.format(monto)
		//String numberAsString = String.valueOf(monto)
		GlobalVariable.monto_origen = monto
		// println("test")
		return rs
	}

	@Keyword
	def consultaBalanceDestino(String destino) {
		Statement stm = connection.createStatement()
		ResultSet rs = stm.executeQuery("SELECT dmcbal FROM BDRQCD.TAP00201 WHERE ACCOUNT_NBR = ('"+ destino +"')")
		rs.next()
		String balance = rs.getString("DMCBAL")
		//balance_antes = Long.parseLong(balance)
		balance_destino = Double.parseDouble(balance)
		println(balance_destino)
		GlobalVariable.monto_destino = balance_destino
		return rs
	}

	@Keyword
	def consultaBalance2(String origen, String destino) {
		println (origen)
		Statement stm = connection.createStatement()
		ResultSet rs = stm.executeQuery("SELECT dmcbal FROM BDRQCD.TAP00201 WHERE ACCOUNT_NBR = ('"+ origen +"')")
		rs.next()
		String balance = rs.getString("DMCBAL")
		double monto = Double.parseDouble(balance)
		//formateador.format(monto)
		//String numberAsString = String.valueOf(monto)
		GlobalVariable.monto_origend = monto

		ResultSet rs2 = stm.executeQuery("SELECT dmcbal FROM BDRQCD.TAP00201 WHERE ACCOUNT_NBR = ('"+ destino +"')")

		rs2.next()
		String balance2 = rs2.getString("DMCBAL")
		double monto2 = Double.parseDouble(balance2)
		//formateador.format(monto2)
		//String numberAsString2 = String.valueOf(monto2)
		GlobalVariable.monto_destinod = monto2

		log.logInfo ("-------------------------------------------------------------------------------")
		log.logInfo ("-------------------------------------------------------------------------------")
		log.logInfo ("Resultados Corrida :")
		log.logInfo ("El balance antes de la transaccione es de :"+"Cuenta Origen -"+GlobalVariable.monto_origen+"  Cuenta Destino -"+GlobalVariable.monto_destino)
		log.logInfo ("El balance despues de la transaccione es de :"+"Cuenta Origen -"+GlobalVariable.monto_origend+"  Cuenta Destino -"+GlobalVariable.monto_destinod)
		log.logInfo ("-------------------------------------------------------------------------------")
		log.logInfo ("-------------------------------------------------------------------------------")
		log.logInfo ("-------------------------------------------------------------------------------")
		return rs

	}


	//Consultar codigo de transaccion
	@Keyword
	def consultaCodTransa(String origen) {


		Statement stm = connection.createStatement()

		ResultSet rs = stm.executeQuery("select TLTRD2 from BDRQCP.PST00101 where ACCOUNT_NUMBER ='"+origen+"'  AND (TLTSTS = 'O') AND (TLTPST = 'P' or TLTPST = 'O') order by TLTRD2 desc")

		rs.next()
		GlobalVariable.Numero_transaccion = rs.getString("TLTRD2")


		return rs

	}



	//Closing the connection

	@Keyword

	def closeDatabaseConnection() {

		if(connection != null && !connection.isClosed()){

			connection.close()

		}

		connection = null

	}



	@Keyword

	def calculo(String montos, String origen, String destino,  String imp) {

		float impuesto = 0
		Statement stm = connection.createStatement()
		ResultSet or = stm.executeQuery("SELECT dmcbal FROM BDRQCD.TAP00201 WHERE ACCOUNT_NBR = ('"+ origen +"')")
		or.next()
		String balanceOrigen = or.getString("DMCBAL")
		double balanceO = Double.parseDouble(balanceOrigen)


		//Statement stm = connection.createStatement()
		ResultSet ds = stm.executeQuery("SELECT dmcbal FROM BDRQCD.TAP00201 WHERE ACCOUNT_NBR = ('"+ destino +"')")
		ds.next()
		String balanceDestino = ds.getString("DMCBAL")
		double balanceD = Double.parseDouble(balanceDestino)


		double monto = Long.parseLong(montos)

		if(imp == "no"){

			println("Se ejecuta la condicion")
			impuesto = monto * 0.0015
		}

		balance_despues =  balance_origen - impuesto - monto
		balance_despues = Double.parseDouble(df.format(balance_despues).toString())
		balance_destino = balance_destino + monto

		println("Este es el balance actual en la DB: " +balanceO + " Este es el balance Calculado por la funcion: " +balance_despues+ " La cuenta esta exenta :"+imp)
		println("Este es el balance actual del beneficiario en la DB: " +balanceD + " Este es el balance actual del beneficiario calculado por la funcion: " +balance_destino)


		if( balanceO == balance_despues && balanceD == balance_destino ){

			println("EL CASO DE PRUEBA PASO")
		}else
			println("EL CASO DE PRUEBA Fallo")

		return or
	}




	@Keyword

	def datapoolCuentas(){


		Statement stm = connection.createStatement()  //Conexion a base de datos Signature
		Statement stm2 = connection2.createStatement()  //Conexion a base de datos SQL

		ResultSet rs = stm.executeQuery("select  CUNTID Cedula,ln.ACCOUNT_NBR Cuenta, ln.Account_type TipoCuenta, ln.CURRENT_BALANCE Balance from  BDRQCD.CUP00901 cp9 inner join BDRQCD.CUP00301 cp3 on cp3.CUNBR = cp9.CUX1CS inner join BDRQCD.TAP00201 ln on ln.ACCOUNT_NBR = substr(cp9.CUX1AC,2,10) where ln.dmstat = 1 and CUNTID != ''")
		ResultSet rs2

		FileWriter fw = new FileWriter("C:/Users/ldesoto/Desktop/datapool/DP_Usuarios_Activos.csv");
		//int x = 1;

		fw.append("CEDULA,CUENTA,TIPO CUENTA,BALANCE,USERNAME,PASSWORD,TIPODOC,IDENTIFICACION\n");
		while (rs.next()  ) {

			rs2 = stm2.executeQuery("SELECT TOP (10) [UserName],[Password], REPLACE([DocumentNumber],'-','') CEDULA FROM [IBKPerData].[dbo].[Users] WHERE REPLACE([DocumentNumber],'-','') ='"+rs.getString(1)+"'")

			fw.append(rs.getString(1));
			fw.append(',');
			fw.append(rs.getString(2));
			fw.append(',');
			fw.append(rs.getString(3));
			fw.append(',');
			fw.append(rs.getString(4));
			fw.append(',');
			fw.append(rs2.getString(1));
			fw.append(',');
			fw.append(rs2.getString(2));
			fw.append(',');
			fw.append(rs2.getString(3));
			fw.append(',');
			fw.append(rs2.getString(4));
			fw.append('\n');

		}



		fw.flush();
		fw.close();


		return connection

		// connection.close();
		// connection2.close();

	}


	@Keyword
	def getBalance(String monto) {

		// declara la cadena como un objeto S1 S2

		// 8 Longitud de una cadena RockStar
		System.out.println("Longitud de una cadena es:" + monto.length());


		GlobalVariable.montoLgt = monto.length()


		String data;
		int j = 0, i = 0, num;
		num = monto.length();



		while(i < num){
			System.out.println(monto.substring(j, i+1));
			j++;
			i++;
		}




	}


	@Keyword
	def consultaTasa(String moneda) {


		//Statement stm = connection.createStatement()

		//ResultSet rs = stm.executeQuery("select PEMXCO,PEMXVE,row_number() over (order by momone) r from V7COVENDAT.CVSEGPEN WHERE pcanca = '2019' and momone = '"+moneda+"' and CVSEGM = 'R' order by r desc")

		rs.next()
		String tasaco = rs.getString("PEMXCO")
		double tasaCO1 = Double.parseDouble(tasaco)
		String tasave = rs.getString("PEMXVE")
		double tasaVE1 = Double.parseDouble(tasave)


		//formateador.format(monto)
		//String numberAsString = String.valueOf(monto)
		//GlobalVariable.monto_origen = monto

		println("La tasa actual es de :"+  tasaCO1)
		println("La tasa actual es de :"+  tasaVE1)



		GlobalVariable.TasaCO =  tasaCO1
		GlobalVariable.TasaVE = tasaVE1
		return rs
	}

}
