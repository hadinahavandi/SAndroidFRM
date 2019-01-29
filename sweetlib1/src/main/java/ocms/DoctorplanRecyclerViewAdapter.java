package ocms;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mojtaba.materialdatetimepicker.utils.PersianCalendar;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.Calendar;
import java.util.List;

import common.Message;
import fileshop.File;
import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;
import layout.MenuFragment;
import layout.PurchaseFragment;

public class DoctorplanRecyclerViewAdapter extends RecyclerView.Adapter<DoctorplanRecyclerViewAdapter.ViewHolder> {
	private final List<Doctorplan> mValues;
	private final DoctorplanFragment.OnListFragmentInteractionListener mListener;
	public MainActivity theActivity;
	public DoctorplanRecyclerViewAdapter(List<Doctorplan> items, DoctorplanFragment.OnListFragmentInteractionListener listener) {
		mValues = items;
		mListener = listener;
	}
	@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_doctorplan, parent, false);
			return new ViewHolder(view);
		}
	@Override
		public void onBindViewHolder(final ViewHolder holder, final int position) {
			holder.mItem = mValues.get(position);
			holder.mView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					theActivity.SharedConf_ItemID =holder.mItem.getId();
					theActivity.showFragment(DoctorplanItemFragment.class);
				}
			});
			long st=Long.parseLong(mValues.get(position).getStart_time());
			st*=1000;
        long et=Long.parseLong(mValues.get(position).getEnd_time());
        et*=1000;
		PersianCalendar pc=new PersianCalendar(st);
		Calendar stc = Calendar.getInstance();
        stc.setTimeInMillis(st);
        Calendar etc = Calendar.getInstance();
        etc.setTimeInMillis(et);
			holder.Start_time.setText(" ساعت " + stc.get(Calendar.HOUR_OF_DAY)+" و "+stc.get(Calendar.MINUTE) + " دقیقه");
			if(holder.mItem.getOff()>0)
			{
				holder.btn_reserve.setText(holder.btn_reserve.getText().toString()+" با "+holder.mItem.getOff()+"٪ تخفیف");
			}
			holder.btn_reserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
					new LovelyStandardDialog(theActivity)
							.setButtonsColor(Color.parseColor("#FFC01059"))
							.setTitle("نحوه پرداخت")
							.setMessage("")
							.setPositiveButton("کسر از اعتبار موجود", new View.OnClickListener() {
								@Override
								public void onClick(View v) {
								    order(mValues.get(position).getId());

								}
							})
							.setNegativeButton("انتقال به درگاه بانکی", new View.OnClickListener() {
								@Override
								public void onClick(View v) {
                                    PurchaseFragment frg=(PurchaseFragment)theActivity.showFragment(PurchaseFragment.class);
                                    final MainActivity theAct=theActivity;
                                    frg.setThePurchaseCompleteListener(new PurchaseFragment.onPurchaseCompleteListener() {
										@Override
										public void OnPurchaseComplete() {
                                            theAct.routeToIndex();
											order(mValues.get(position).getId());

										}
									});
                                    Log.d("price", String.valueOf(mValues.get(position).getPrice()));
                                    frg.Pay((100-mValues.get(position).getOff())*mValues.get(position).getPrice()/100);

								}
							})
							.show();

                }
            });
		}
		private void order(final long id)
        {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    String UserName= theActivity.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.USERNAME,"0");
                    String Password= theActivity.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.PASSWORD,"0");
                    final Message msg= new Doctorplan(theActivity).reserve(id,UserName,Password,theActivity.SharedConf_presencetype);
                    theActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new LovelyStandardDialog(theActivity)
                                    .setButtonsColor(Color.parseColor("#FFC01059"))
                                    .setTitle("خطا")
                                    .setMessage(msg.getMessageText())
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show();

                        }
                    });
                }
            });
        }
	@Override
		public int getItemCount() {
			return mValues.size();
		}
	public class ViewHolder extends RecyclerView.ViewHolder {
		public final View mView;
		public final TextView Start_time;
        public final Button btn_reserve;

		public Doctorplan mItem;

		public ViewHolder(View view) {
			super(view);
			mView = view;
			Typeface face= Typeface.createFromAsset(theActivity.getAssets(),"fonts/IRANSansMobile.ttf");
			Start_time = view.findViewById(R.id.start_time);
			Start_time.setTypeface(face);
            btn_reserve = view.findViewById(R.id.btn_reserve);
            btn_reserve.setTypeface(face);
		}
		@Override
		public String toString() {
			return super.toString();
		}
	}	}