package com.homeScreen;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.divyangdantani.eventmanagementfinal.R;

import java.util.List;

/**
 * Created by Divyang Dantani on 3/8/2018.
 */

public class MemberAdepter extends RecyclerView.Adapter<MemberAdepter.MemberViewHolder> {

    Dialog memberDialog;
    Member_Dialog member_dialog;
    private Context mCtx;
    private Activity activity;
    private List<MemberModel> memberList;



    public MemberAdepter(Context mCtx, List<MemberModel> memberList) {
        this.mCtx = mCtx;
        this.memberList = memberList;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_member,null);
        final MemberViewHolder holder = new MemberViewHolder(view);

        //Dialog initialization

        member_dialog = new Member_Dialog(mCtx);
        //memberDialog.setContentView(R.layout.dailog_member_details);
        //memberDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        member_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
/*
       activity = new Activity();
       //activity.setContentView(R.layout.dailog_member_details);
       activity.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
       activity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
*/
        holder.member_item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView dialog_member_name = member_dialog.findViewById(R.id.dialog_member_name);
                TextView dialog_member_mobileno = member_dialog.findViewById(R.id.dialog_member_mobileno);
                //ImageView dialog_member_image = memberDialog.findViewById(R.id.dialog_member_image);

                dialog_member_name.setText(memberList.get(holder.getAdapterPosition()).getmName());
                dialog_member_mobileno.setText(memberList.get(holder.getAdapterPosition()).getmMobileno());
                //dialog_member_image.setImageResource(memberList.get(holder.getAdapterPosition()).getImage());
                Toast.makeText(mCtx,"Test Clicked : "+String.valueOf(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                /*Intent i = new Intent(mCtx,Member_Dialog.class);
                mCtx.startActivity(i);*/
                member_dialog.show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final MemberViewHolder holder, int position) {
        MemberModel member = memberList.get(position);

        holder.txtName.setText(member.getmName());
        holder.txtEmail.setText(member.getmEmail());
        holder.txtFlatno.setText(member.getmFlatno());
        holder.txtMobileno.setText(member.getmMobileno());


        //holder.mImageView.setImageDrawable(mCtx.getResources().getDrawable(member.getmImage()));

    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder{

        CardView member_item;
        ImageView mImageView;
        TextView txtName,txtMobileno,txtFlatno,txtEmail;

        public MemberViewHolder(View itemView) {
            super(itemView);
            member_item = itemView.findViewById(R.id.member_cardView);
            mImageView = itemView.findViewById(R.id.img_member);
            txtName = itemView.findViewById(R.id.member_name);
            txtMobileno = itemView.findViewById(R.id.member_mobileno);
            txtFlatno = itemView.findViewById(R.id.member_flatno);
            txtEmail = itemView.findViewById(R.id.member_email);
        }
    }
}
