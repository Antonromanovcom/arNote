package com.antonromanov.arnote.service.investment.xmlparse;

import com.antonromanov.arnote.exceptions.MoexXmlResponseMarshalingException;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.enums.Currencies;
import com.antonromanov.arnote.model.investing.response.DividendRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.common.CommonMoexDoc;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class XmlHandlerImpl implements XmlHandler {

    @Override
    public Optional<ConsolidatedDividendsRs> parse(ResponseEntity<String> response) {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        List<DividendRs> divList = new ArrayList<>();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(response.getBody())));
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("rows");
            NodeList rows = nodeList.item(0).getChildNodes();

            for (int i = 0; i < rows.getLength(); i++) {
                Node nNode = rows.item(i + 1);
                if (nNode != null && nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    divList.add(DividendRs.builder()
                            .currencyId(Currencies.RUB)
                            .value(Double.valueOf(element.getAttribute("value")))
                            .registryCloseDate(element.getAttribute("registryclosedate"))
                            .build());
                }
            }

            ConsolidatedDividendsRs result = ConsolidatedDividendsRs
                    .builder()
                    .dividendList(divList)
                    .build();

            result.calculateSum();

            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /*@Override
    public CommonMoexDoc marshall(ResponseEntity<String> response) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(MoexDocumentRs.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();
            return (CommonMoexDoc) un.unmarshal(new InputSource(new StringReader(response.getBody())));
        } catch (JAXBException e) {
            return null;
        }
    }*/

    @Override
    public CommonMoexDoc marshall(ResponseEntity<String> response, Class<?> moexClass) {
        if (response.getBody() == null) {
            throw new MoexXmlResponseMarshalingException();
        } else {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(moexClass);
                Unmarshaller un = jaxbContext.createUnmarshaller();
                return (CommonMoexDoc) un.unmarshal(new InputSource(new StringReader(response.getBody())));
            } catch (JAXBException e) {
                throw new MoexXmlResponseMarshalingException();
            }
        }
    }
}
