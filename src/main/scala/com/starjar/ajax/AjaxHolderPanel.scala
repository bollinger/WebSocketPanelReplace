package com.starjar.ajax

import com.googlecode.wicket.jquery.core.panel.LoadingPanel
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.protocol.ws.api.WebSocketBehavior
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage


case class AjaxKey(depth: Int, path: String)


/**
  * Created by peter on 29/08/17.
  */
class AjaxHolderPanel(id: String, key: AjaxKey) extends Panel(id) {

  private var clicked = false

  private var connected: Option[ConnectedMessage] = None


  override def onInitialize(): Unit = {
    super.onInitialize()

    getPage match {
      case wsPage: WebSocketPage =>
        println("AjaxHolderPanel onInitialize: Page is a web socket page.")
      case _=>
        println("AjaxHolderPanel onInitialize: Page is not a web socket page")
    }



    add(new WebSocketBehavior {
      override def onConnect(message: ConnectedMessage): Unit = {
        super.onConnect(message)
        // make some noise when connected.
        println(s"AjaxHolderPanel: web socket connected. key=$key")
        connected = Some(message)
      }
    })




    setOutputMarkupId(true)

    add(new Label("panelName", s"$key"))



//    val listModel = new IModel[java.util.List[AjaxKey]] {
//      override def getObject(): java.util.List[AjaxKey] = {
//
//        val nextDepth = key.depth + 1
//        if (key.depth < 5) {
//          (0 to key.depth).map{idx =>
//
//            AjaxKey(nextDepth, key.path + "/" )
//
//          }
//        } else {
//          List()
//        }
//
//
//        woInTxResult.outTxs.asJava
//      }
//
//      override def setObject(obj: util.List[AjaxKey]) = ???
//
//      override def detach() = ???
//    }




    val loadingPanel = new LoadingPanel("thePanel")
    add(loadingPanel)


    add(new AjaxLink("btn") {
      override def onConfigure(): Unit = {
        super.onConfigure()
        setVisible(!clicked)
      }

      override def onClick(target: AjaxRequestTarget): Unit = {
        clicked = true
        val nextDepth = key.depth + 1

        val nextPath = key.path + s"/$nextDepth"

        val dataPanel = new AjaxHolderPanel("thePanel", AjaxKey(nextDepth, nextPath) )
        loadingPanel.replaceWith(dataPanel)

        target.add(AjaxHolderPanel.this)



        getPage match {
          case wsPage: WebSocketPage =>
            println("Page is a web socket page.")
          case _=>
            println("Page is not a web socket page")
        }


      }
    })

  }

}
