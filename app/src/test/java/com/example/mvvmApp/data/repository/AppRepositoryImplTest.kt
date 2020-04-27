package com.example.mvvmApp.data.repository

import com.example.mvvmApp.MyApplication
import com.example.mvvmApp.data.datasource.api.APIDataSource
import com.example.mvvmApp.data.datasource.db.Table1App
import com.example.mvvmApp.data.datasource.local.LocalDataSource
import com.example.mvvmApp.data.preferences.AppPreferences
import com.example.mvvmApp.data.preferences.AppPreferencesImpl
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(MyApplication::class)
class AppRepositoryImplTest {
    lateinit var application: MyApplication
    @Before
    fun setUp() {
        application = mock<MyApplication>()
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        AppRepositoryImpl.destroyInstance()
        Dispatchers.resetMain()
    }

    @Test
    fun testGetAllTableData_0() = runBlocking {
        val tableData: List<Table1App> = listOf()
        val appRepositoryImpl =
            AppRepositoryImpl.getInstance(
                application,
                object : LocalDataSource by mock<LocalDataSource>() {
                    override fun getAllTableApps() = tableData
                    override var appPreferences = mock<AppPreferences>()
                },
                object : APIDataSource by mock<APIDataSource>() {
                }
            )
        val result = appRepositoryImpl.getAllTable1()
        Assert.assertEquals(0, result.size)
    }

    @Test
    fun testGetAllTableData_1() = runBlocking {
        val tableData = listOf(Table1App(1,"table1", "app1"))
        val appRepositoryImpl =
            AppRepositoryImpl.getInstance(
                application,
                object : LocalDataSource by mock<LocalDataSource>() {
                    override fun getAllTableApps() = tableData
                    override var appPreferences = mock<AppPreferences>()
                },
                object : APIDataSource by mock<APIDataSource>() {
                }
            )
        val result = appRepositoryImpl.localDataSource.getAllTableApps()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals(1L, result[0].id)
        Assert.assertEquals("table1", result[0].tableId)
        Assert.assertEquals("app1", result[0].tableName)
    }

    @Test
    fun testGetAllTableData_2() = runBlocking {
        val tableData = listOf(Table1App(1, "table1","app1"), Table1App(2, "table2","app2"))
        val appRepositoryImpl =
            AppRepositoryImpl.getInstance(
                application,
                object : LocalDataSource by mock<LocalDataSource>() {
                    override fun getAllTableApps() = tableData
                    override var appPreferences = mock<AppPreferences>()
                },
                object : APIDataSource by mock<APIDataSource>() {
                }
            )
        val result = appRepositoryImpl.localDataSource.getAllTableApps()
        Assert.assertEquals(2, result.size)
        Assert.assertEquals(1L, result[0].id)
        Assert.assertEquals("table1", result[0].tableId)
        Assert.assertEquals("app1", result[0].tableName)
        Assert.assertEquals(2L, result[1].id)
        Assert.assertEquals("table2", result[1].tableId)
        Assert.assertEquals("app2", result[1].tableName)
    }

    @Test
    fun testGetAppsByApplicationId_Found() = runBlocking {
        val appRepositoryImpl =
            AppRepositoryImpl.getInstance(
                application,
                object : LocalDataSource by mock<LocalDataSource>() {
                    override fun getTableById(applicationId: String): Table1App? {
                        return Table1App(1, "table1","app1")
                    }
                    override var appPreferences = mock<AppPreferences>()
                },
                object : APIDataSource by mock<APIDataSource>() {
                }
            )
        val result = appRepositoryImpl.localDataSource.getTableById("app1")
        Assert.assertEquals(1L, result?.id)
        Assert.assertEquals("app1", result?.tableId)
    }

    @Test
    fun testGetAppsByApplicationId_NotFound() = runBlocking {
        val appRepositoryImpl =
            AppRepositoryImpl.getInstance(
                application,
                object : LocalDataSource by mock<LocalDataSource>() {
                    override fun getTableById(applicationId: String): Table1App? {
                        return null
                    }
                    override var appPreferences = mock<AppPreferences>()
                },
                object : APIDataSource by mock<APIDataSource>() {
                }
            )
        val result = appRepositoryImpl.localDataSource.getTableById("app1")
        Assert.assertEquals(null, result)
    }

    @Test
    fun testInsertAppIfNotExists_Added() = runBlocking {
        val table1App = Table1App(0, "table1","app1")
        val argumentCaptor = argumentCaptor<Table1App>()
        val localDataSource = mock<LocalDataSource> {
            on { insertTableAppIfNotExists(argumentCaptor.capture()) } doReturn 1
        }
        val appRepositoryImpl =
            AppRepositoryImpl.getInstance(
                application,
                localDataSource,
                object : APIDataSource by mock<APIDataSource>() {
                }
            )
        val result = appRepositoryImpl.localDataSource.insertTableAppIfNotExists(table1App)
        Assert.assertEquals(1, result)
        Assert.assertEquals(table1App.id, argumentCaptor.firstValue.id)
        Assert.assertEquals(table1App.tableId, argumentCaptor.firstValue.tableId)
        Assert.assertEquals(table1App.tableName, argumentCaptor.firstValue.tableName)
    }

    @Test
    fun testDeleteTable1App() = runBlocking {
        val table1App = Table1App(0, "table1","app1")
        val argumentCaptor = argumentCaptor<Table1App>()
        val localDataSource = mock<LocalDataSource> {
            on { deleteTableApp(argumentCaptor.capture()) } doReturn 1
        }
        val appRepositoryImpl =
            AppRepositoryImpl.getInstance(
                application,
                localDataSource,
                object : APIDataSource by mock<APIDataSource>() {
                }
            )
        val result = appRepositoryImpl.deleteTable1(table1App)
        Assert.assertEquals(1, result)
        Assert.assertEquals(table1App.id, argumentCaptor.firstValue.id)
        Assert.assertEquals(table1App.tableId, argumentCaptor.firstValue.tableId)
        Assert.assertEquals(table1App.tableName, argumentCaptor.firstValue.tableName)
    }

    @Test
    fun initializeValuesOfAppPreferences() {
        val appPreferences = mock<AppPreferencesImpl>()

        AppRepositoryImpl.getInstance(
            application,
            object : LocalDataSource by mock<LocalDataSource>() {
                override val appPreferences = appPreferences
            },
            object : APIDataSource by mock<APIDataSource>() {
            }
        )

        verify(appPreferences, times(1)).initDisableSetting = eq(true)
        verify(appPreferences, times(1)).initIsFirstRun = eq(false)
    }
}