package me.lam.maidong.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      /*  if (ShareUitls.getString(getActivity(), "TODAY", "null") != null && !ShareUitls.getString(context, "TODAY", "null").equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))) {
            Toast.makeText(getActivity(), "请重新登录", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getActivity(), Login.class);
            startActivity(i);
            getActivity().finish();
            return;

        }else {*/
           // InternetUtils. internet2(getActivity());
      //  }
    }
    @Override
    public void onResume() {
        super.onResume();
       // MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
      //  MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

}
