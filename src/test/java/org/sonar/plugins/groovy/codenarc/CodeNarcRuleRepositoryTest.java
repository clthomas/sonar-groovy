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

package org.sonar.plugins.groovy.codenarc;

import java.util.List;

import org.junit.Ignore;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.XMLRuleParser;

import static org.fest.assertions.Assertions.assertThat;

public class CodeNarcRuleRepositoryTest {

  @Ignore
  public void test() {
    CodeNarcRuleRepository repo = new CodeNarcRuleRepository(new XMLRuleParser());
    assertThat(repo.createRules().size()).isEqualTo(287);
    List<Rule> rules = repo.createRules();
    assertThat(rules.size()).isEqualTo(318);
    assertThat(rules.get(47).getParams()).hasSize(1);
    assertThat(rules.get(47).getParams().get(0).getDefaultValue()).isEqualToIgnoringCase("false");
  }
}
