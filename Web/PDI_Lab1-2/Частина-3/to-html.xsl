<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/customs">
        <html>
            <head>
                <title>Митні декларації - Стецюк Михайло</title>
                <style>
                    * { margin: 0; }
                    .declaration {
                        border: 2px solid gray;
                        background: wheat;
                        padding: 8px;
                    }
                    ul {
                        padding-left: 16px;
                    }
                </style>
            </head>
            <body>
                <xsl:apply-templates select="declaration"/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="declaration">
        <div class="declaration">
            <h2>
                Декларація
                <xsl:if test="@id">
                    №<xsl:value-of select="@id"/>
                </xsl:if>
            </h2>
            <p>
                <b>ПІБ: </b>
                <xsl:value-of select="lastname"/><xsl:text> </xsl:text>
                <xsl:value-of select="firstname"/><xsl:text> </xsl:text>
                <xsl:value-of select="patronym"/>
            </p>
            <p>
                <b>Маршрут: </b>
                <xsl:value-of select="origin"/> — <xsl:value-of select="destination"/>
            </p>
            <p>
                <b>Наявні гроші: </b>
                <xsl:value-of select="money"/><xsl:text> </xsl:text><xsl:value-of select="money/@currency"/>
            </p>

            <xsl:if test="baggage/item">
                <p><b>Багаж:</b></p>
                <ul>
                    <xsl:apply-templates select="baggage/item"/>
                </ul>
            </xsl:if>
        </div>
    </xsl:template>

    <xsl:template match="item">
        <li>
            <xsl:value-of select="name"/>:
            <xsl:value-of select="amount"/><xsl:text> </xsl:text><xsl:value-of select="amount/@unit"/>,
            <xsl:value-of select="value"/><xsl:text> </xsl:text><xsl:value-of select="value/@currency"/>
            <xsl:if test="taxes/tax">
                <ul>
                    <xsl:apply-templates select="taxes/tax"/>
                </ul>
            </xsl:if>
        </li>
    </xsl:template>

    <xsl:template match="tax">
        <li>
            <xsl:value-of select="@type"/>
            (<xsl:value-of select="@rate"/>%):
            <xsl:value-of select="."/>
        </li>
    </xsl:template>

</xsl:stylesheet>