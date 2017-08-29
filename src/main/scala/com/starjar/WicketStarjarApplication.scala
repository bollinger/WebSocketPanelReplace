package com.starjar

import com.starjar.ws.HomePage
import org.apache.wicket.Application
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.protocol.ws.WebSocketSettings
import org.apache.wicket.protocol.ws.api.WebSocketPushBroadcaster
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage
import org.apache.wicket.protocol.ws.api.message.IWebSocketPushMessage



object WicketStarjarApplication {
  var wicketApplicationKey: String = null

  def get(): WicketStarjarApplication = {
    Application.get(wicketApplicationKey).asInstanceOf[WicketStarjarApplication]
  }




  def broadcast(connected: ConnectedMessage, msg: IWebSocketPushMessage): Unit = {
    val webSocketSettings = WebSocketSettings.Holder.get(WicketStarjarApplication.get())
    val broadcaster = new WebSocketPushBroadcaster(webSocketSettings.getConnectionRegistry)
    broadcaster.broadcast(connected, msg)
  }


}

/**
  * Created by peter on 28/08/17.
  */
class WicketStarjarApplication extends WebApplication {
  override def getHomePage() = classOf[HomePage]
//  override def getHomePage() = classOf[AjaxHomePage]

  override def init() {
    super.init()
    WicketStarjarApplication.wicketApplicationKey = getApplicationKey

    mountPage("index.html", getHomePage())
  }

}
