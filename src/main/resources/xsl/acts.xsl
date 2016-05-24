<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:b="http://ftn.uns.ac.rs/xml" version="1.0">

    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
            </head>
            <body>
                <xsl:apply-templates select="//b:preamble"/>
                <h2>
                    <xsl:value-of select="b:act/@title"/>
                </h2>
                <xsl:apply-templates select="b:act/*[not(self::b:preamble)] "/>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="b:preamble">
        <p>
            <xsl:value-of select="."/>
        </p>
    </xsl:template>
    <xsl:template match="b:part">
        <h3>
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
    <xsl:template match="b:head">
        <h4>
            <xsl:value-of select="@title"/>
        </h4>
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
    <xsl:template match="b:section">
        <h5>
            <xsl:value-of select="@title"/>
        </h5>
        <xsl:choose>
            <xsl:when test="count(b:subsection) &gt; 0">
                <xsl:apply-templates select="b:subsection"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates select="b:article"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="b:subsection">
        <h5>
            <xsl:value-of select="@title"/>
        </h5>
        <xsl:apply-templates select="b:article"/>
    </xsl:template>
    <xsl:template match="b:article">
        <div>
            <xsl:attribute name="id">
                <xsl:value-of select="@id" />
            </xsl:attribute>

            <!-- Add modify link -->
            <xsl:choose>
                <xsl:when test="@modify">
                    <a>
                        <xsl:attribute name="href">
                            <xsl:value-of select="@modify"/>
                        </xsl:attribute>
                        Modify
                    </a>
                </xsl:when>
            </xsl:choose>

            <h5>
                Član <xsl:value-of select="@id+1"/>
            </h5>
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
    <xsl:template match="b:paragraph">
        <p><xsl:value-of select="b:text"/></p>
        <xsl:choose>
            <xsl:when test="count(b:item) &gt; 0">
                <ul>
                    <xsl:apply-templates select="b:item"/>
                </ul>
            </xsl:when>
            <xsl:otherwise>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
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
    <xsl:template match="b:subitem">
        <xsl:choose>
            <xsl:when test="count(b:ident) &gt; 0">
                <ul>
                    <xsl:apply-templates select="b:ident"/>
                </ul>
            </xsl:when>
            <xsl:otherwise>
                <ul>
                    <xsl:value-of select="."/>
                </ul>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="b:ident">
        <li>
            <xsl:value-of select="."/>
        </li>
    </xsl:template>
</xsl:stylesheet>

