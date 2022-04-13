package mx.com.moonsmileh.sciencecodedemo.ui.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.com.moonsmileh.sciencecodedemo.R
import mx.com.moonsmileh.sciencecodedemo.databinding.MainFragmentBinding
import mx.com.moonsmileh.sciencecodedemo.ui.viewmodel.MainViewModel

class MainFragment : Fragment(), DriversAdapter.DriversEvents {

    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        context?.let {
            viewModel.onCreate(it)
            binding.recyclerViewDrivers.layoutManager = LinearLayoutManager(it)
        }

        viewModel.driversLiveData.observe(viewLifecycleOwner) {
            binding.recyclerViewDrivers.adapter = DriversAdapter(it, this)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.bestSuitableShipment.observe(viewLifecycleOwner) {
            context?.let { context -> showAssignedShipment(it, context) }
        }
    }


    override fun onDriverClickListener(driver: String) {
        viewModel.getBestSuitableShipment(driver)
    }

    private fun showAssignedShipment(message: String, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.company_name))

        builder.setMessage(message)

        builder.setPositiveButton(getString(R.string.positive_button)) { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }
}