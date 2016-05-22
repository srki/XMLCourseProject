<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/">
        <html>
            <body>
                <xsl:apply-templates select="//preamble"/>
                <h2>
                    <xsl:value-of select="act/@title"/>
                </h2>
                <xsl:apply-templates select="act/*[not(self::preamble)] "/>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="preamble">
        <p>
            <xsl:value-of select="."/>
        </p>
    </xsl:template>
    <xsl:template match="part">
        <h3>
            <xsl:value-of select="@title"/>
        </h3>
        <xsl:choose>
            <xsl:when test="count(head) &gt; 0">
                <ol>
                    <xsl:apply-templates select="head"/>
                </ol>
            </xsl:when>
            <xsl:otherwise>
                <ol>
                    <xsl:apply-templates select="article"/>
                </ol>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="head">
        <h4>
            <xsl:value-of select="@title"/>
        </h4>
        <xsl:choose>
            <xsl:when test="count(section) &gt; 0">
                <ol>
                    <xsl:apply-templates select="section"/>
                </ol>
            </xsl:when>
            <xsl:when test="count(subsection) &gt; 0">
                <ol>
                    <xsl:apply-templates select="subsection"/>
                </ol>
            </xsl:when>
            <xsl:when test="count(article) &gt; 0">
                <ol>
                    <xsl:apply-templates select="article"/>
                </ol>
            </xsl:when>
            <xsl:otherwise/>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="section">
        <h5>
            <xsl:value-of select="@title"/>
        </h5>
        <xsl:choose>
            <xsl:when test="count(subsection) &gt; 0">
                <ol>
                    <xsl:apply-templates select="subsection"/>
                </ol>
            </xsl:when>
            <xsl:otherwise>
                <ol>
                    <xsl:apply-templates select="article"/>
                </ol>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="subsection">
        <h5>
            <xsl:value-of select="@title"/>
        </h5>
        <ol>
            <xsl:apply-templates select="article"/>
        </ol>
    </xsl:template>
    <xsl:template match="article">
        <h6>
            Clan
            <xsl:value-of select="@id+1"/>
        </h6>
        <xsl:choose>
            <xsl:when test="count(paragraph) &gt; 0">
                <xsl:apply-templates select="paragraph"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="."/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="paragraph">
        <p><xsl:value-of select="text"/></p>
        <xsl:choose>
            <xsl:when test="count(item) &gt; 0">
                <xsl:apply-templates select="item"/>
            </xsl:when>
            <xsl:otherwise>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="item">
        <xsl:choose>
            <xsl:when test="count(subitem) &gt; 0">
                <xsl:apply-templates select="subitem"/>
            </xsl:when>
            <xsl:otherwise>
                -<xsl:value-of select="."/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="subitem">
        <xsl:choose>
            <xsl:when test="count(ident) &gt; 0">
                <xsl:apply-templates select="ident"/>
            </xsl:when>
            <xsl:otherwise>
                <ul>
                    <xsl:value-of select="."/>
                </ul>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="ident">
        <xsl:value-of select="."/>
    </xsl:template>
</xsl:stylesheet>

