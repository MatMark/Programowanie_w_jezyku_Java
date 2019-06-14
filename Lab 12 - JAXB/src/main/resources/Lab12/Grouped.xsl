<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        version="1.0"
>
    <xsl:output method="html"/>
    <xsl:template match="/">
    <html>
    <head>
    </head>
    <body>
    <table border="1">
    <tr>
        <th>ID</th>
        <th>Produkt</th>
        <th>Wystawiający</th>
        <th>Adres</th>
    </tr>
        <xsl:for-each select="offers/offer">
            <tr align="center">
                <td><xsl:value-of select="id"/></td>
                <td>Nazwa: <xsl:value-of select="product/name"/><br></br>Cena: <xsl:value-of select="product/price"/>zł<br></br>Typ: <xsl:value-of select="product/type"/></td>
                <td>Imie: <xsl:value-of select="seller/firstName"/><br></br>Nazwisko: <xsl:value-of select="seller/lastName"/><br></br>Tel: <xsl:value-of select="seller/tel"/></td>
                <td>Ulica: <xsl:value-of select="address/street"/><br></br>Miasto: <xsl:value-of select="address/city"/><br></br>Kod pocztowy: <xsl:value-of select="address/postCode"/></td>
            </tr>
        </xsl:for-each>
    </table>
    </body>
    </html>
    </xsl:template>
</xsl:stylesheet>