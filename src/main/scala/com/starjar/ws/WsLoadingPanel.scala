package com.starjar.ws

import com.googlecode.wicket.jquery.core.panel.LoadingPanel
import com.starjar.WicketStarjarApplication
import com.starjar.ajax.WebSocketPage
import org.apache.wicket.event.IEvent
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.protocol.ws.api.WebSocketBehavior
import org.apache.wicket.protocol.ws.api.event.WebSocketPushPayload
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage
import org.log4s._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Failure
import scala.util.Success

object WsLoadingPanel {
  protected val log: Logger = getLogger
}

/**
  * Created by peter on 29/08/17.
  */
class WsLoadingPanel(id: String, path: String) extends Panel(id) {

  private var connected: Option[ConnectedMessage] = None

  private var thePanel: WebMarkupContainer = _

  private val PanelWicketName = "thePanel"



  private def log(msg: String): Unit = {
    WsLoadingPanel.log.info(s"WsHolderPanel path=$path $msg")
  }


  override def onInitialize(): Unit = {
    super.onInitialize()

    setOutputMarkupId(true)

    add(new Label("panelName", s"path=$path"))

    thePanel = new LoadingPanel("thePanel")
    add(thePanel)

    // DEBUG CODE
    getWebPage match {
      case wsPage: WebSocketPage =>
        log(s"onInitialize getWebPage is a web socket page. ${wsPage.connected}")
        // could trigger loading here? if connected message is present? race conditions?
        // startLoadingInBackground() // BODGE pretend web socket onConnect is called. // Fails with an exception later.
      case _ =>
        log(s"onInitialize getWebPage is NOT a web socket page.")
    }


    add(new WebSocketBehavior {
      override def onConnect(message: ConnectedMessage): Unit = {
        super.onConnect(message)

        // if the parent of this panel is replacing another panel from a broadcast event..
        // the onConnected method will never be triggered.

        log(s"WsHolderPanel path=$path    Web socket connected.")
        connected = Some(message)

        startLoadingInBackground()
      }
    })
  }

  private def startLoadingInBackground(): Unit = {
    val promise = FakeDatabase.load(path)
    promise.future.onComplete { r =>
      connected.foreach{ conMsg =>
        WicketStarjarApplication.broadcast(conMsg, ResBroadcastMessage(r))
      }
//      broadcast(ResBroadcastMessage(r))
    }

  }


//  private def broadcast(msg: IWebSocketPushMessage): Unit = {
//    log(s"in broadcast")
//    connected.foreach{ conmsg =>
//      val application = WicketStarjarApplication.get()
//
//      val webSocketSettings = WebSocketSettings.Holder.get(application)
//      val broadcaster = new WebSocketPushBroadcaster(webSocketSettings.getConnectionRegistry)
//      broadcaster.broadcast(conmsg, msg)
//    }
//  }






  override def onEvent(event: IEvent[_]) {
    super.onEvent(event)


    event.getPayload match {
      case payload: WebSocketPushPayload =>
        log(s"Got a web socket push payload  ${payload.getMessage}")

        payload.getMessage match {
          case ResBroadcastMessage(subjectResult) =>
            subjectResult match {
              case Success(res) =>
                if (res.path == path) {
                  val resultPanel = new WsResultPanel("thePanel", res)
                  thePanel.replaceWith(resultPanel)
                  payload.getHandler.add(WsLoadingPanel.this)
                }

              case Failure(ex) =>
                ex.printStackTrace()
            }
        }
      case _ =>
    }
  }


}
