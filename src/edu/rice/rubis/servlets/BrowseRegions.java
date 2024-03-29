package edu.rice.rubis.servlets;

import edu.rice.rubis.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 * Builds the html page with the list of all region in the database
 * @author <a href="mailto:cecchet@rice.edu">Emmanuel Cecchet</a> and <a href="mailto:julie.marguerite@inrialpes.fr">Julie Marguerite</a>
 * @version 1.0
 */
public class BrowseRegions extends HttpServlet
{
  

  private void printError(String errorMsg, ServletPrinter sp)
  {
    sp.printHTMLheader("RUBiS ERROR: Browse Regions");
    sp.printHTML("<h3>Your request has not been processed due to the following error :</h3><br>");
    sp.printHTML(errorMsg);
    sp.printHTMLfooter();
  }

  /**
   * Display the list of regions
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param response a <code>HttpServletResponse</code> value
   * @exception IOException if an error occurs
   * @exception ServletException if an error occurs
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    ServletPrinter sp = null;
    sp = new ServletPrinter(response, "BrowseRegions", getServletContext());
    sp.printHTMLheader("RUBiS: Available regions");
    sp.printHTML("<h2>Currently available regions</h2><br>");
 
    Context initialContext = null;
    try
    {
      initialContext = new InitialContext();
    } 
    catch (Exception e) 
    {
      printError("Cannot get initial context for JNDI: " +e+"<br>", sp);
      return ;
    }

    SB_BrowseRegions sb_browseRegions = null;
    try 
    {
      sb_browseRegions = (SB_BrowseRegions)PortableRemoteObject.narrow(initialContext.lookup("SB_BrowseRegionsBean"), SB_BrowseRegions.class);
    } 
    catch (Exception e)
    {
      printError("Cannot lookup SB_BrowseRegions: " +e+"<br>", sp);
      return ;
    }
    String list;
    try 
    {
      list = sb_browseRegions.getRegions();
    } 
    catch (Exception e)
    {
      printError("Cannot get the list of regions: " +e+"<br>", sp);
      return ;
    }

    sp.printHTML(list);
    sp.printHTMLfooter();
  }

}
