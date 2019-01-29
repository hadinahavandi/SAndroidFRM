package ocms;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;

public class DoctorRecyclerViewAdapter extends RecyclerView.Adapter<DoctorRecyclerViewAdapter.ViewHolder> {
	private final List<Doctor> mValues;
	private final DoctorFragment.OnListFragmentInteractionListener mListener;
	public MainActivity theActivity;
	public DoctorRecyclerViewAdapter(List<Doctor> items, DoctorFragment.OnListFragmentInteractionListener listener) {
		mValues = items;
		mListener = listener;
	}
	@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_doctor, parent, false);
			return new ViewHolder(view);
		}
	@Override
		public void onBindViewHolder(final ViewHolder holder, int position) {
			holder.mItem = mValues.get(position);
			holder.mView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					theActivity.SharedConf_ItemID =holder.mItem.getId();
					theActivity.showFragment(DoctorItemFragment.class);
				}
			});
			holder.Name.setText(String.valueOf(mValues.get(position).getName()));
			holder.Family.setText(String.valueOf(mValues.get(position).getFamily()));
//			holder.Education.setText(String.valueOf(mValues.get(position).getEducation()));
			holder.Matabaddress.setText(String.valueOf(mValues.get(position).getMatabaddress()));
		if(String.valueOf(mValues.get(position).getPhoto_flu()).trim().length()>3)
			Picasso.with(theActivity.getApplicationContext()).load(Constants.SITEURL+String.valueOf(mValues.get(position).getPhoto_flu())).into(holder.Photo_flu);
		}
	@Override
		public int getItemCount() {
			return mValues.size();
		}
	public class ViewHolder extends RecyclerView.ViewHolder {
		public final View mView;
		public final TextView Name;
		public final TextView Family;
//		public final TextView Education;
		public final TextView Matabaddress;
		public final ImageView Photo_flu;
		public Doctor mItem;
		public ViewHolder(View view) {
			super(view);
			mView = view;
			Typeface face= Typeface.createFromAsset(theActivity.getAssets(),"fonts/IRANSansMobile.ttf");
			Name = view.findViewById(R.id.name);
			Name.setTypeface(face);
			Family = view.findViewById(R.id.family);
			Family.setTypeface(face);
//			Education = view.findViewById(R.id.education);
//			Education.setTypeface(face);
			Matabaddress = view.findViewById(R.id.matabaddress);
			Matabaddress.setTypeface(face);
			Photo_flu = view.findViewById(R.id.photo_flu);
		}
		@Override
		public String toString() {
			return super.toString();
		}
	}	}