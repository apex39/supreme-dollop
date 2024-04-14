import com.example.supremedollop.model.Salesman
import com.example.supremedollop.repository.SalesmanRepository
import com.example.supremedollop.utils.MainDispatcherRule
import com.example.supremedollop.view.SalesmanViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SalesmanViewModelTest {
    @Mock
    private lateinit var mockRepository: SalesmanRepository

    private lateinit var viewModel: SalesmanViewModel
    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = SalesmanViewModel(mockRepository)
    }

    @Test
    fun `search query updates results after debounce`() = runTest {
        // Given
        val mockSalesmen = listOf(
            Salesman("Alice", listOf("1000")),
            Salesman("Bob", listOf("1001"))
        )
        val results = mutableListOf<List<Salesman>>()
        `when`(mockRepository.findSalesmenByArea("100")).thenReturn(mockSalesmen)

        // When
        val job = launch {
            viewModel.salesmenResults.collect { results.add(it) }
        }

        // Then
        viewModel.updateSearchQuery("10")
        advanceUntilIdle()
        viewModel.updateSearchQuery("100")
        advanceTimeBy(1100)

        assertEquals(listOf(emptyList(), mockSalesmen), results)

        job.cancel()  // Clean up
    }

    @Test
    fun `no results are emitted when query is empty`() = runTest {
        // Given
        `when`(mockRepository.findSalesmenByArea("")).thenReturn(emptyList())

        val results = mutableListOf<List<Salesman>>()

        // When
        val job = launch {
            viewModel.salesmenResults.collect { results.add(it) }
        }

        // Then
        viewModel.updateSearchQuery("")
        advanceTimeBy(1100)
        viewModel.updateSearchQuery("")
        advanceTimeBy(1100)

        assertEquals(listOf(emptyList<Salesman>()), results)

        job.cancel()
    }
}
