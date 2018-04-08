package ocms;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;

public class SpecialityRecyclerViewAdapter extends RecyclerView.Adapter<SpecialityRecyclerViewAdapter.ViewHolder> {
	private final List<Speciality> mValues;
	private final SpecialityFragment.OnListFragmentInteractionListener mListener;
	public MainActivity theActivity;
	public SpecialityRecyclerViewAdapter(List<Speciality> items, SpecialityFragment.OnListFragmentInteractionListener listener) {
		mValues = items;
		mListener = listener;
	}
	@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_speciality, parent, false);
			return new ViewHolder(view);
		}
	@Override
		public void onBindViewHolder(final ViewHolder holder, int position) {
			holder.mItem = mValues.get(position);
			holder.mView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					theActivity.SharedConf_ItemID =holder.mItem.getId();
					theActivity.SharedConf_speciality =holder.mItem.getId();
					theActivity.showFragment(DoctorFragment.class);
				}
			});
			holder.Title.setText(String.valueOf(mValues.get(position).getTitle()));
		}
	@Override
		public int getItemCount() {
			return mValues.size();
		}
	public class ViewHolder extends RecyclerView.ViewHolder {
		public final View mView;
		public final TextView Title;
		public Speciality mItem;
		public ViewHolder(View view) {
			super(view);
			mView = view;
			Typeface face= Typeface.createFromAsset(theActivity.getAssets(),"fonts/IRANSansMobile.ttf");
			Title = view.findViewById(R.id.title);
			Title.setTypeface(face);
		}
		@Override
		public String toString() {
			return super.toString();
		}
	}	}