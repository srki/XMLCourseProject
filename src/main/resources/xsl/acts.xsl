<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
   <xsl:template match="/">
      <html>
         <body>
            <xsl:apply-templates select="//preamble" />
            <h2>
               <xsl:value-of select="act/@title" />
            </h2>
            <xsl:apply-templates />
         </body>
      </html>
   </xsl:template>
   <xsl:template match="preamble">
      <p>
         <xsl:value-of select="." />
      </p>
   </xsl:template>
   <xsl:template match="part">
      <h3>
         <xsl:value-of select="@title" />
      </h3>
      <xsl:choose>
         <xsl:when test="count(head) &gt; 0">
            <ol>
               <xsl:apply-templates select="head" />
            </ol>
         </xsl:when>
         <xsl:otherwise>
            <ol>
               <xsl:apply-templates select="article" />
            </ol>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="head">
      <h4>
         <xsl:value-of select="@title" />
      </h4>
      <xsl:choose>
         <xsl:when test="count(section) &gt; 0">
            <ol>
               <xsl:apply-templates select="section" />
            </ol>
         </xsl:when>
         <xsl:when test="count(subsection) &gt; 0">
            <ol>
               <xsl:apply-templates select="subsection" />
            </ol>
         </xsl:when>
         <xsl:when test="count(article) &gt; 0">
            <ol>
               <xsl:apply-templates select="article" />
            </ol>
         </xsl:when>
         <xsl:otherwise />
      </xsl:choose>
   </xsl:template>
   <xsl:template match="section">
      <h5>
         <xsl:value-of select="@title" />
      </h5>
      <xsl:choose>
         <xsl:when test="count(subsection) &gt; 0">
            <ol>
               <xsl:apply-templates select="subsection" />
            </ol>
         </xsl:when>
         <xsl:otherwise>
            <ol>
               <xsl:apply-templates select="article" />
            </ol>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="subsection">
      <h5>
         <xsl:value-of select="@title" />
      </h5>
      <ol>
         <xsl:apply-templates select="article" />
      </ol>
   </xsl:template>
   <xsl:template match="article">
      <p>
         Clan
         <xsl:value-of select="@id+1" />
      </p>
      <xsl:apply-templates select="paragraph" />
   </xsl:template>
   <xsl:template match="paragraph">
      <p>
         <xsl:value-of select="." />
      </p>
   </xsl:template>
   <xsl:template match="item" />
   <xsl:template match="subitem" />
   <xsl:template match="ident" />
</xsl:stylesheet>

