<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:b="http://ftn.uns.ac.rs/xml"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:output method="xml" version="1.0"
                encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">

            <fo:layout-master-set>
                <fo:simple-page-master master-name="act">
                    <fo:region-body margin="1in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="act">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-family="Calibri" font-size="9px" font-weight="bold" padding="30px">
                        <xsl:value-of select="//b:preamble"/>
                    </fo:block>
                    <fo:block text-indent="24px" font-family="Arial, Helventica">
                        <fo:inline font-weight="bold">
                            <xsl:value-of select="b:act/@title"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block font-family="Times-Roman, Courier, Times, Symbol">
                        <xsl:apply-templates select="b:act/*[not(self::b:preamble)] "/>
                    </fo:block>

                </fo:flow>
                <!-- Page content goes here -->
            </fo:page-sequence>

        </fo:root>
    </xsl:template>

    <xsl:template match="b:preamble">
        <fo:block>
            <xsl:value-of select="."/>
        </fo:block>
    </xsl:template>
    <xsl:template match="b:part">
        <fo:block margin-top="25px" text-indent="22px">
            <xsl:value-of select="@title"/>
        </fo:block>
        <xsl:choose>
            <xsl:when test="count(b:head) &gt; 0">
                <fo:block>
                    <xsl:apply-templates select="b:head"/>
                </fo:block>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates select="b:article"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="b:head">
        <fo:block margin-top="20px" text-indent="18px">
            <xsl:value-of select="@title"/>
        </fo:block>
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
        <fo:block margin-top="16px" text-indent="16px">
            <xsl:value-of select="@title"/>
        </fo:block>
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
        <fo:block margin-top="12px" text-indent="14px">
            <xsl:value-of select="@title"/>
        </fo:block>
        <xsl:apply-templates select="b:article"/>
    </xsl:template>
    <xsl:template match="b:article">
        <fo:block>

            <fo:block margin-top="10px" text-indent="12px">
                &#269;lan <xsl:value-of select="@id+1"/>
            </fo:block>
            <fo:block font-size="12px">
                <xsl:choose>
                    <xsl:when test="count(b:paragraph) &gt; 0">
                        <xsl:apply-templates select="b:paragraph"/>
                    </xsl:when>
                    <xsl:otherwise>

                        <xsl:value-of select="."/>


                    </xsl:otherwise>
                </xsl:choose>
            </fo:block>
        </fo:block>
    </xsl:template>
    <xsl:template match="b:paragraph">
        <fo:block text-indent="8px"><xsl:value-of select="b:text"/></fo:block>
        <xsl:choose>
            <xsl:when test="count(b:item) &gt; 0">
                <fo:block>
                    <xsl:apply-templates select="b:item"/>
                </fo:block>
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
                <fo:block>
                    <!--<fo:list-item>-->

                    <!--<fo:list-item-label>-->
                    <fo:block><xsl:value-of select="position()"/>)<xsl:value-of select="."/></fo:block>
                    <!--</fo:list-item-body>-->
                    <!--</fo:list-item>-->

                </fo:block>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="b:subitem">
        <xsl:choose>
            <xsl:when test="count(b:ident) &gt; 0">
                <fo:block>
                    <xsl:apply-templates select="b:ident"/>
                </fo:block>
            </xsl:when>
            <xsl:otherwise>
                <fo:block>
                    <fo:block>(<xsl:value-of select="position()"/>)<xsl:value-of select="."/></fo:block>
                </fo:block>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="b:ident">
        <fo:block>
            -<xsl:value-of select="."/>
        </fo:block>
    </xsl:template>
</xsl:stylesheet>