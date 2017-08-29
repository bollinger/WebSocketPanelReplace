package com.starjar.ws

import java.util

import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.model.IModel

import scala.collection.JavaConverters._

/**
  * Created by peter on 29/08/17.
  */
class WsResultPanel(id: String, res: Res) extends Panel(id) {


  override def onInitialize(): Unit = {
    super.onInitialize()

    add(new Label("path", res.path))


    val model = new IModel[java.util.List[String]]{
      override def getObject: java.util.List[String] = {
        res.childPaths.asJava
      }

      override def setObject(obj: util.List[String]) = {}

      override def detach() = {}
    }

    val list = new ListView[String]("list", model) {
      override def populateItem(item: ListItem[String]): Unit = {
        val cp = item.getModelObject
        item.add(new WsLoadingPanel("child", cp))
      }
    }
    add(list)


  }

}
