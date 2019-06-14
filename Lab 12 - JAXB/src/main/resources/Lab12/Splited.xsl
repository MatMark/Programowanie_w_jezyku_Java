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
        <th>Nazwa</th>
        <th>Cena</th>
        <th>Typ</th>

        <th>Imię</th>
        <th>Nazwisko</th>
        <th>Telefon</th>

        <th>Ulica</th>
        <th>Miasto</th>
        <th>Kod pocztowy</th>
    </tr>
        <xsl:for-each select="offers/offer">
            <tr align="center">
                <td><xsl:value-of select="id"/></td>
                <td><xsl:value-of select="product/name"/></td>
                <td><xsl:value-of select="product/price"/>zł</td>
                <td><xsl:value-of select="product/type"/></td>
                <td><xsl:value-of select="seller/firstName"/></td>
                <td><xsl:value-of select="seller/lastName"/></td>
                <td><xsl:value-of select="seller/tel"/></td>
                <td><xsl:value-of select="address/street"/></td>
                <td><xsl:value-of select="address/city"/></td>
                <td><xsl:value-of select="address/postCode"/></td>
            </tr>
        </xsl:for-each>
    </table>
    </body>
    </html>
    </xsl:template>
</xsl:stylesheet>