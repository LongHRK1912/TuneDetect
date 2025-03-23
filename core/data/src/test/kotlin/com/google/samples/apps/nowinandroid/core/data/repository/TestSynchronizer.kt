/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hrk.apps.hrkdev.core.data.repository

import com.hrk.apps.hrkdev.core.data.Synchronizer
import com.hrk.apps.hrkdev.core.datastore.ChangeListVersions
import com.hrk.apps.hrkdev.core.datastore.HRKPreferencesDataSource

/**
 * Test synchronizer that delegates to [HRKPreferencesDataSource]
 */
class TestSynchronizer(
    private val HRKPreferences: HRKPreferencesDataSource,
) : Synchronizer {
    override suspend fun getChangeListVersions(): ChangeListVersions =
        HRKPreferences.getChangeListVersions()

    override suspend fun updateChangeListVersions(
        update: ChangeListVersions.() -> ChangeListVersions,
    ) = HRKPreferences.updateChangeListVersion(update)
}
