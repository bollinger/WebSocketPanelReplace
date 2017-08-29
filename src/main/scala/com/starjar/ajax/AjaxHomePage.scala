package com.starjar.ajax

import com.starjar.WicketStarjarApplication
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.protocol.ws.WebSocketSettings
import org.apache.wicket.protocol.ws.api.WebSocketBehavior
import org.apache.wicket.protocol.ws.api.WebSocketPushBroadcaster
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage
import org.apache.wicket.protocol.ws.api.message.IWebSocketPushMessage


trait WebSocketPage {
  var connected: Option[ConnectedMessage] = None

  def broadcast(msg: IWebSocketPushMessage): Unit = {
    println("in broadcast")
    connected.foreach{ conmsg =>
      val application = WicketStarjarApplication.get()

      val webSocketSettings = WebSocketSettings.Holder.get(application)
      val broadcaster = new WebSocketPushBroadcaster(webSocketSettings.getConnectionRegistry)
      broadcaster.broadcast(conmsg, msg)
    }
  }
}


/**
  *
  *
  * Created by peter on 28/08/17.
  */
class AjaxHomePage extends WebPage with WebSocketPage {


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


    add(new AjaxHolderPanel("holderPanel", AjaxKey(0, "/") ) )
  }




}
