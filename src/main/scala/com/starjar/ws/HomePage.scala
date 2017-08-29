package com.starjar.ws

import com.starjar.ajax.WebSocketPage
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.protocol.ws.api.WebSocketBehavior
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage

/**
  *
  *
  * HolderPanel has a WebSocketBehavior.
  * When a client is connected, background workers start to load data.
  *
  * The data is then broadcast to the page, which in turn uses web sockets to update the clients page with additional panels.
  *
  * If the added panels also make use of web sockets:-
  *   The web socket onConnect is not called. (Is this a bug or feature, as the page already has a web socket connection?)
  *
  *
  *
  *
  *
  * Created by peter on 28/08/17.
  */
class HomePage extends WebPage with WebSocketPage {


  override def onInitialize(): Unit = {
    super.onInitialize()


    add(new WebSocketBehavior {
      override def onConnect(message: ConnectedMessage): Unit = {
        super.onConnect(message)
        // make some noise when connected.
        println(s"AjaxHomePage: web socket connected.")
        connected = Some(message)
      }
    })


    //add(new HolderPanel("panel", FakeData.WoCInTxId) )
    add(new WsLoadingPanel("holderPanel", "A") )
  }
}
