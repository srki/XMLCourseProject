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

                <xsl:apply-templates select="//b:preamble"/>
                <h2 style="text-align: center">
                    <xsl:value-of select="b:act/@title"/>
                </h2>
                <xsl:apply-templates select="b:act/*[not(self::b:preamble)] "/>
            </body>
        </html>
    </xsl:template>

    <!-- Preamble -->
    <xsl:template match="b:preamble">
        <p>
            <xsl:value-of select="."/>
        </p>
    </xsl:template>

    <!-- Part -->
    <xsl:template match="b:part">
        <h3 style="text-align: center">
            <xsl:value-of select="@title"/>
        </h3>
        <xsl:choose>
            <xsl:when test="count(b:head) &gt; 0">
                <ol>
                    <xsl:apply-templates select="b:head"/>
                </ol>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates select="b:article"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Head -->
    <xsl:template match="b:head">
        <h3 style="text-align: center">
            <xsl:value-of select="@title"/>
        </h3>
        <xsl:choose>
            <xsl:when test="count(b:section) &gt; 0">
                <xsl:apply-templates select="b:section"/>
            </xsl:when>
            <xsl:when test="count(b:subsection) &gt; 0">
                <xsl:apply-templates select="b:subsection"/>
            </xsl:when>
            <xsl:when test="count(b:article) &gt; 0">
                <xsl:apply-templates select="b:article"/>
            </xsl:when>
            <xsl:otherwise/>
        </xsl:choose>
    </xsl:template>

    <!-- Section -->
    <xsl:template match="b:section">
        <h3 style="text-align: center">
            <xsl:value-of select="@title"/>
        </h3>
        <xsl:choose>
            <xsl:when test="count(b:subsection) &gt; 0">
                <xsl:apply-templates select="b:subsection"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates select="b:article"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Subsection -->
    <xsl:template match="b:subsection">
        <h3 style="text-align: center">
            <xsl:value-of select="@title"/>
        </h3>
        <xsl:apply-templates select="b:article"/>
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
                        <xsl:attribute name="ng-disabled">
                            currentStatus != 'amendment'
                        </xsl:attribute>
                        <div style="text-align: center">Modify</div>
                    </a>
                </xsl:when>
            </xsl:choose>

            <h3 style="text-align: center">
                <xsl:attribute name="ng-click">scroll(<xsl:value-of select="@id"/>)</xsl:attribute>
                Član <xsl:value-of select="1 + count(./preceding::b:article | ./ancestor::b:article)"/>.
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
        <p>
            <xsl:value-of select="b:text"/>
        </p>
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
        <xsl:choose>
            <xsl:when test="count(b:subitem) &gt; 0">
                <xsl:apply-templates select="b:subitem"/>
            </xsl:when>
            <xsl:otherwise>
                <li>
                    <xsl:value-of select="."/>
                </li>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Subitem -->
    <xsl:template match="b:subitem">
        <xsl:choose>
            <xsl:when test="count(b:ident) &gt; 0">
                <ol>
                    <xsl:apply-templates select="b:ident"/>
                </ol>
            </xsl:when>
            <xsl:otherwise>
                <ol>
                    <xsl:value-of select="."/>
                </ol>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Ident -->
    <xsl:template match="b:ident">
        <li>
            <xsl:value-of select="."/>
        </li>
    </xsl:template>
</xsl:stylesheet>

