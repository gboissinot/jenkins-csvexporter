<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" indent="yes"/>

    <xsl:param name="SVN_URL"/>

    <xsl:template match="//scm">
        <scm class="hudson.scm.SubversionSCM">
            <locations>
                <hudson.scm.SubversionSCM_-ModuleLocation>
                    <remote><xsl:value-of select="$SVN_URL"/></remote>
                    <local>.</local>
                </hudson.scm.SubversionSCM_-ModuleLocation>
            </locations>
            <excludedRegions/>
            <includedRegions/>
            <excludedUsers/>
            <excludedRevprop/>
            <excludedCommitMessages/>
            <workspaceUpdater class="hudson.scm.subversion.UpdateUpdater"/>
        </scm>
    </xsl:template>

    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
        </xsl:copy>
    </xsl:template>

</xsl:stylesheet>