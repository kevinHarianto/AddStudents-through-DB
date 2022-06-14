package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Student;

public class AddServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      Connection connection = null;
      PreparedStatement ps = null;
      try {
        String check = "select * from students where id = ?;";
        String query1 = "insert into students values(?,?,?);";
        String query2 = "select * from students;";
        connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/java3" , "java3", "java3");
        ps = connection.prepareStatement(check);
        ps.setInt(1, Integer.parseInt(request.getParameter("id")));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) 
          request.setAttribute("message", "ID already exist.");
        else {
          ps = connection.prepareStatement(query1);
          ps.setInt(1, Integer.parseInt(request.getParameter("id")));
          ps.setString(2, request.getParameter("name"));
          ps.setString(3, request.getParameter("email"));
          ps.executeUpdate();
          
          ps = connection.prepareStatement(query2);
          rs = ps.executeQuery();
          ArrayList<Student> students = new ArrayList<>();
          while (rs.next()) {
            Student student = new Student(rs.getInt("id"), rs.getString("name"),
              rs.getString("email"));
            students.add(student);
          }
          request.setAttribute("students", students);
          request.setAttribute("message", "Currently we have: ");
        }
      } catch (Exception e) {
       request.setAttribute("message", "Something is wrong: " + e);       
       //throw new ServletException(e);
      } finally {
        if (connection != null) 
          try {
            connection.close();
        } catch (SQLException ex) {
          Logger.getLogger(AddServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ps != null)
          try {
            ps.close();
        } catch (SQLException ex) {
          Logger.getLogger(AddServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
      getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
      
    } 

    @Override
    public void init() throws ServletException {
      final String SQL_CREATE_TABLE = 
          "create table if not exists students ("
          + "  id int not null primary key,"
          + "  name varchar(20) not null," 
          + "  email varchar(100) not null);";
      Connection connection = null;
      PreparedStatement ps = null;      
      try {
        connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/java3" , "java3", "java3");
        ps = connection.prepareStatement(SQL_CREATE_TABLE);
        ps.execute();
      } catch (SQLException ex) {
        Logger.getLogger(AddServlet.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
        if (connection != null) 
          try {
            connection.close();
        } catch (SQLException ex) {
          Logger.getLogger(AddServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ps != null)
          try {
            ps.close();
        } catch (SQLException ex) {
          Logger.getLogger(AddServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
      }      
    }    
      
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
