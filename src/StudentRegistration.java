

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.fastinfoset.sax.Properties;


public class StudentRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public StudentRegistration() {
        super();
    }

    Connection con= null;
    Statement st=null;
    ServletContext context = null;
	public void init(ServletConfig config) throws ServletException {
		try {
			/*java.io.File f= new java.io.File("D:\\eclipse-workspace-Servlets\\Student RegisterApp\\src\\db.properties");
			FileInputStream fis = new FileInputStream(f);
			Properties p = new Properties();
			p.load(fis);
			*/
			context = config.getServletContext();
			String d = context.getInitParameter("driver");
			String url= context.getInitParameter("url");
			String us= context.getInitParameter("user");
			String pw= context.getInitParameter("pass");
			Class.forName(d);
			con = DriverManager.getConnection(url,us,pw);
			//Class.forName("oracle.jdbc.driver.OracleDriver"); 
			//con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "Oracle12c");
					//DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","Oracle12c");
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		} 
		
	}
	public void destroy() {
		try {
			con.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		int no=Integer.parseInt(request.getParameter("sno"));
		String name= request.getParameter("sname");
		int marks=Integer.parseInt(request.getParameter("marks"));
		try {
			PreparedStatement ps= con.prepareStatement("insert into student values(?,?,?)");
			ps.setInt(1, no);
			ps.setString(2, name);
			ps.setInt(3, marks);
			int n= ps.executeUpdate();
			if(n!=0) {
				out.println("one record inserted");
				RequestDispatcher rd = request.getRequestDispatcher("/register.html");
				rd.include(request, response);
			} else {
				out.println("record not inserted");
				RequestDispatcher rd = request.getRequestDispatcher("/register.html");
				rd.include(request, response);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}

}
