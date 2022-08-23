package com.antonromanov.arnote.domain.investing.service.xmlparse;

/*@Service
@Slf4j*/
public class XmlHandlerImpl /*implements XmlHandler*/ {

 /*   @Override
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

    @Override
    public CommonMoexDoc marshall(ResponseEntity<String> response, Class<?> moexClass) {
        if (response.getBody() == null) {
            log.error("Ошибка маршелинга - ответ пришел, но бади пустое!");
            throw new MoexXmlResponseMarshalingException();
        } else {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(moexClass);
                Unmarshaller un = jaxbContext.createUnmarshaller();
                return (CommonMoexDoc) un.unmarshal(new InputSource(new StringReader(response.getBody())));
            } catch (JAXBException e) {
                log.error("Ошибка маршелинга: {}", e.getMessage());
                throw new MoexXmlResponseMarshalingException();
            }
        }
    }*/
}
