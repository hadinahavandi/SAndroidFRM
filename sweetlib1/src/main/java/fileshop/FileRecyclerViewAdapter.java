package fileshop;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import common.SweetDisplayScaler;
import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;

public class FileRecyclerViewAdapter extends RecyclerView.Adapter<FileRecyclerViewAdapter.ViewHolder> {
	private final List<File> mValues;
	private final FileFragment.OnListFragmentInteractionListener mListener;
	public MainActivity theActivity;
	public FileRecyclerViewAdapter(List<File> items, FileFragment.OnListFragmentInteractionListener listener) {
		mValues = items;
		mListener = listener;
	}
	@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_file, parent, false);
			return new ViewHolder(view);
		}
	@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			holder.mItem = mValues.get(position);
			final ViewHolder myHolder=holder;
			holder.mView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					theActivity.SharedConf_ItemID=myHolder.mItem.getId();
					Log.d("sss",String.valueOf(myHolder.mItem.getId()));
					theActivity.showFragment(FileItemFragment.class);
				}
			});
			holder.Title.setText(String.valueOf(holder.mItem.getTitle()));
			holder.Description.setText(String.valueOf(holder.mItem.getDescription()));
		}
	@Override
		public int getItemCount() {
			return mValues.size();
		}
	public class ViewHolder extends RecyclerView.ViewHolder {
		public final View mView;
		public final TextView Title;
		public final TextView Description;
		public final ImageView imgLogo;
		public File mItem;
		public ViewHolder(View view) {
			super(view);
			mView = view;
			Typeface face= Typeface.createFromAsset(theActivity.getAssets(),"fonts/IRANSansMobile.ttf");
			Title = view.findViewById(R.id.title);
			Title.setTypeface(face);
			Description = view.findViewById(R.id.desc);
			Description.setTypeface(face);
			imgLogo=view.findViewById(R.id.voicestoryicon);
			SweetDisplayScaler scaler=new SweetDisplayScaler(theActivity);
			imgLogo.getLayoutParams().width=scaler.WidthPercentToPixel(25);
            imgLogo.getLayoutParams().height=imgLogo.getLayoutParams().width;
		}
		@Override
		public String toString() {
			return super.toString();
		}
	}	}