/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lesk;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 *
 * @author Neophron
 */
public class XmlTester {
    
    public static void main(String[] args) throws Exception {
        String str;
        WSD wsd = new WSD();
        for (int i = 10; i < 45; i++) {
            str = "E:\\semcor\\brownv\\tagfiles\\br-a" + Integer.toString(i) + ".xml";
            System.out.println(str);
            todo(str, wsd);
        }
    }

    public static void todo(String path, WSD wsd) {
        try {
            int normal = 0, all = 0;
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("p");//p
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);//p
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    NodeList slist = nNode.getChildNodes();//s
                    for (int tmp = 0; tmp < slist.getLength(); tmp++) {
                        Node sNode = slist.item(tmp);//s
                        if (sNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element el = (Element) nNode;
                            NodeList list = sNode.getChildNodes();//under s
                            String str = "";
                            List<Pair<String, String>> ls = new ArrayList<>();
                            for (int i = 0; i < list.getLength(); i++) {
                                Node node = list.item(i);
                                if (node.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eElement = (Element) node;
                                    str += eElement.getTextContent() + " ";
                                    if ("wf".equals(eElement.getTagName())) {
                                        if ("done".equals(eElement.getAttribute("cmd"))) {
                                            if (!"".equals(eElement.getAttribute("lemma"))) {
                                                ls.add(new Pair(eElement.getAttribute("lemma").replaceAll("_", " "),
                                                        eElement.getAttribute("lexsn")));
                                            }
                                        }
                                    }
                                }
                            }
                            //System.out.println(str);
                            str = str.replaceAll("\n", " ");
                            str = str.replaceAll("_", " ");
                            //System.out.println(str);
                            for (Pair p : ls) {
                                Pair res = wsd.wsd(str, p.getKey().toString());
                                if (res.getKey().equals("")) {
                                    continue;
                                }
                                if (res.getKey().equals(p.getValue().toString())) {
                                    normal++;
                                }
                                all++;
                                if (all % 10 == 0) {
                                    //System.out.print("i'm working ");
                                    //System.out.println(all);
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(normal);
            System.out.println(all);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

}
