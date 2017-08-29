package com.starjar

import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.servlet.GuiceServletContextListener

/**
  * Created by peter on 29/08/17.
  */
class MyGuiceServletConfig extends GuiceServletContextListener {
  override def getInjector(): Injector = {
    try {
      Guice.createInjector(new MyModule())

    } catch {
      case rt: RuntimeException =>
        rt.printStackTrace()
        throw rt
    }
  }
}
