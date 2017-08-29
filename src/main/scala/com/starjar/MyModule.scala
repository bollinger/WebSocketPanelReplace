package com.starjar

import javax.servlet.http.HttpServlet

import com.google.inject.Singleton
import com.google.inject.servlet.ServletModule
import org.apache.wicket.protocol.http.WicketFilter

/**
  * Created by peter on 29/08/17.
  */
class MyModule extends ServletModule {

  def serve(loc: String, c: Class[_ <: HttpServlet]) {
    serve(loc).`with`(c)
  }


  override def configureServlets() {
    super.configureServlets()

    println("MyModule configureServlets")

    //install(new BeeEngineModule)


    val wicketApplicationPath = "/*"
    val wicketFilterName = "wicket.websocket"

    val wicketParams = new java.util.HashMap[String, String]()

    val wicketApplicationClassName = classOf[WicketStarjarApplication].getName
    println(s"application class name = $wicketApplicationClassName")
    wicketParams.put("applicationClassName", wicketApplicationClassName)       // ContextParamWebApplicationFactory.wicketApplicationClassName
    wicketParams.put("configuration", "deployment")
    wicketParams.put("filterName", wicketFilterName)
    wicketParams.put(WicketFilter.FILTER_MAPPING_PARAM, wicketApplicationPath)  // ContextParamWebApplicationFactory.

    val wicketFilterClass = classOf[org.apache.wicket.protocol.ws.javax.JavaxWebSocketFilter]  // for tomcat 8 and wicket 7

    bind(wicketFilterClass).in(classOf[Singleton])
    filter(wicketApplicationPath).through(wicketFilterClass, wicketParams)
  }
}
