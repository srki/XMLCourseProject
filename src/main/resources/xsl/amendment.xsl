<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns="http://www.w3.org/1999/xhtml" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:b="http://ftn.uns.ac.rs/xml" version="1.0">

    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
            </head>
            <body style="font-family: Calibri">
                <div style="text-align: center; margin-bottom: 15px">
                    <img src="http://localhost:8080/img/novi-sad-logo.png" height="96" width="96"/>
                </div>
                <h2 style="text-align: center">GRAD NOVI SAD</h2>

                <h3 style="text-align: center">
                    Amandman
                    <xsl:value-of select="b:amendment/@name"/>
                    za akt
                    <br/>
                    <span>ACT_NAME</span>
                </h3>

                <h2 style="text-align: center">
                    <xsl:choose>
                        <xsl:when test="/b:amendment[@operation='delete']">
                            Briše se
                        </xsl:when>
                        <xsl:when test="/b:amendment[@operation='update']">
                            Menja se
                        </xsl:when>
                        <xsl:when test="/b:amendment[@operation='insert']">
                            Ubacuje se posle
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="b:amendment/@operation"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </h2>

                <xsl:apply-templates select="//b:description"/>
                <xsl:apply-templates select="//b:article"/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="b:block" name="block">
        <p>
            <xsl:value-of select="."/>
        </p>

        <xsl:choose>
            <xsl:when test="@uri">
                <p>
                    <a>
                        <xsl:attribute name="href">
                            <xsl:value-of select="@uri"/>
                        </xsl:attribute>
                        Vidi
                    </a>
                </p>
            </xsl:when>
        </xsl:choose>

    </xsl:template>

    <!-- Description -->
    <xsl:template match="b:description">
        <p>
            <xsl:value-of select="."/>
        </p>
    </xsl:template>

    <!-- Article -->
    <xsl:template match="b:article">
        <div>
            <xsl:attribute name="id">
                <xsl:value-of select="@id"/>
            </xsl:attribute>

            <!-- Add modify link -->
            <xsl:choose>
                <xsl:when test="@modify">
                    <a>
                        <xsl:attribute name="href">
                            <xsl:value-of select="@modify"/>
                        </xsl:attribute>
                        <div style="text-align: center">Modify</div>
                    </a>
                </xsl:when>
            </xsl:choose>

            <h3 style="text-align: center">
                Član
                <xsl:value-of select="@id+1"/>.
            </h3>
            <xsl:choose>
                <xsl:when test="count(b:paragraph) &gt; 0">
                    <xsl:apply-templates select="b:paragraph"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="."/>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>

    <!-- Paragraph -->
    <xsl:template match="b:paragraph">
        <xsl:apply-templates select="b:text/b:block"/>

        <xsl:choose>
            <xsl:when test="count(b:item) &gt; 0">
                <ol>
                    <xsl:apply-templates select="b:item"/>
                </ol>
            </xsl:when>
            <xsl:otherwise>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Item -->
    <xsl:template match="b:item">
        <li>
            <xsl:apply-templates select="b:text/b:block"/>

            <xsl:choose>
                <xsl:when test="count(b:subitem) &gt; 0">
                    <ol>
                        <xsl:apply-templates select="b:subitem"/>
                    </ol>
                </xsl:when>
            </xsl:choose>
        </li>
    </xsl:template>

    <!-- Subitem -->
    <xsl:template match="b:subitem">
        <li>
            <xsl:apply-templates select="b:text/b:block"/>

            <xsl:choose>
                <xsl:when test="count(b:ident) &gt; 0">
                    <ul>
                        <xsl:apply-templates select="b:ident"/>
                    </ul>
                </xsl:when>
            </xsl:choose>
        </li>
    </xsl:template>

    <!-- Ident -->
    <xsl:template match="b:ident">
        <li>
            <xsl:apply-templates select="b:text/b:block"/>
        </li>
    </xsl:template>

</xsl:stylesheet>