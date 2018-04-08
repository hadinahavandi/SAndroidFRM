package ocms;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mojtaba.materialdatetimepicker.utils.PersianCalendar;

import java.util.Calendar;
import java.util.List;

import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;

public class DoctorreserveRecyclerViewAdapter extends RecyclerView.Adapter<DoctorreserveRecyclerViewAdapter.ViewHolder> {
	private final List<Doctorreserve> mValues;
	private final DoctorreserveFragment.OnListFragmentInteractionListener mListener;
	public MainActivity theActivity;
	public DoctorreserveRecyclerViewAdapter(List<Doctorreserve> items, DoctorreserveFragment.OnListFragmentInteractionListener listener) {
		mValues = items;
		mListener = listener;
	}
	@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_doctorreserve, parent, false);
			return new ViewHolder(view);
		}
	@Override
		public void onBindViewHolder(final ViewHolder holder, int position) {
			holder.mItem = mValues.get(position);
			holder.Title.setText(String.valueOf(mValues.get(position).getUser_DoctorName()+" "+String.valueOf(mValues.get(position).getUser_DoctorFamily())));
        long st=Long.parseLong(mValues.get(position).getStartTime());
        st*=1000;
        Calendar stc = Calendar.getInstance();
        stc.setTimeInMillis(st);

        PersianCalendar pc=new PersianCalendar(st);
			holder.Time.setText(" ساعت " + stc.get(Calendar.HOUR_OF_DAY)+" و "+stc.get(Calendar.MINUTE) + " دقیقه" + " روز "+pc.getPersianWeekDayName()+" "+pc.getPersianYear()+"/"+(pc.getPersianMonth()+1)+"/"+pc.getPersianDay());
			int PresenceType=Integer.parseInt(mValues.get(position).getPresencetype_fid());
			if(PresenceType==1)
				holder.Presencetype.setText("مراجعه حضوری");
			else if(PresenceType==2)
				holder.Presencetype.setText("مراجعه به منزل");
		else if(PresenceType==3)
			holder.Presencetype.setText("مشاوره تلفنی");
		}
	@Override
		public int getItemCount() {
			return mValues.size();
		}
	public class ViewHolder extends RecyclerView.ViewHolder {
		public final View mView;
		public final TextView Title;
		public final TextView Time;
		public final TextView Presencetype;
		public Doctorreserve mItem;
		public ViewHolder(View view) {
			super(view);
			mView = view;
			Typeface face= Typeface.createFromAsset(theActivity.getAssets(),"fonts/IRANSansMobile.ttf");
			Title = view.findViewById(R.id.title);
			Title.setTypeface(face);
			Time = view.findViewById(R.id.time);
			Time.setTypeface(face);
			Presencetype = view.findViewById(R.id.presencetype);
			Presencetype.setTypeface(face);
		}
		@Override
		public String toString() {
			return super.toString();
		}
	}	}