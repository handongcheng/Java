package XML;


import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

	//读取配置文件
public class XMLFactory {

	public static Map<String, String> getXMLInfo(String filename){
		Map<String, String> jdbcmap=new HashMap<String, String>();
		File file=new File(filename);
		SAXReader reader=new SAXReader();
		Document doc=null;
		try {
			doc=reader.read(file);
			//System.out.println(doc.asXML());
			Element roots=doc.getRootElement();
			Element root=roots.element("datasource");
			Element jdbc=root.element("jdbc");
			List<Element> propertys=jdbc.elements();
			for(Element property:propertys){
				Attribute nameattribute=property.attribute("name");
				Attribute valueattribute=property.attribute("value");
				if(valueattribute!=null){
					//有name，也有value的时候
					jdbcmap.put(nameattribute.getText(), valueattribute.getText());
				}else{
					//有name，没有value的时候
					jdbcmap.put(nameattribute.getText(), property.getText());
				}
			}
			readBeanXML(roots);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jdbcmap;
	}
	
	private static void readBeanXML(Element roots){
		Element ormmapping=roots.element("orm-mapping");
		Element listelement=ormmapping.element("list");
		List<Element> valuelist=listelement.elements();
	}
	
	public static void main(String[] args) {
		String filename=XMLFactory.class.getResource("/beifengorm.datasource.xml").getFile();
		Map<String,String> map=getXMLInfo(filename);
		Set<String> set=map.keySet();
		Iterator<String> it=set.iterator();
		while(it.hasNext()){
			String key=it.next();
			System.out.println(key+" : "+map.get(key));
		}
	}
}

