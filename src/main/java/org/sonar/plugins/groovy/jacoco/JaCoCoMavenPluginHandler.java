/*
 * Sonar Groovy Plugin
 * Copyright (C) 2010 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.groovy.jacoco;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.sonar.api.batch.maven.MavenPlugin;
import org.sonar.api.batch.maven.MavenPluginHandler;
import org.sonar.api.batch.maven.MavenSurefireUtils;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Project;

public class JaCoCoMavenPluginHandler implements MavenPluginHandler {

  private static final String ARG_LINE_PARAMETER = "argLine";
  private static final String TEST_FAILURE_IGNORE_PARAMETER = "testFailureIgnore";

  private final String groupId;
  private final String artifactId;
  private final String version;

  private JacocoConfiguration configuration;
	@javax.annotation.ParametersAreNonnullByDefault
	private final Settings settings;

	public JaCoCoMavenPluginHandler(JacocoConfiguration configuration, Settings settings) {
    this.configuration = configuration;
		this.settings = settings;
		groupId = MavenSurefireUtils.GROUP_ID;
    artifactId = MavenSurefireUtils.ARTIFACT_ID;
    version = MavenSurefireUtils.VERSION;
  }

  public String getGroupId() {
    return groupId;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public String getVersion() {
    return version;
  }

  public boolean isFixedVersion() {
    return false;
  }

  public String[] getGoals() {
    return new String[] { "test" };
  }

  public void configure(Project project, MavenPlugin plugin) {
    // See SONARPLUGINS-600
	System.out.println("Project " + project);
    String destfilePath = configuration.getReportPath();
    File destfile = project.getFileSystem().resolvePath(destfilePath);


	  if (destfile.exists() && destfile.isFile()) {
      //FIXME make this delete only if not set to reuseReport
      //JaCoCoUtils.LOG.info("Deleting {}", destfile);

      /*if (!destfile.delete()) {
        throw new SonarException("Unable to delete " + destfile);
      }*/
    }

    String argument = configuration.getJvmArgument();

    String argLine = plugin.getParameter(ARG_LINE_PARAMETER);
    argLine = StringUtils.isBlank(argLine) ? argument : argument + " " + argLine;
    JaCoCoUtils.LOG.info("JVM options: {}", argLine);
    plugin.setParameter(ARG_LINE_PARAMETER, argLine);

    plugin.setParameter(TEST_FAILURE_IGNORE_PARAMETER, "true");
  }

}
