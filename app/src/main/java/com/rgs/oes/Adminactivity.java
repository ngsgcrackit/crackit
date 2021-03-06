package com.rgs.oes;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class Adminactivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    CharSequence s;
    float rs;
    String TAG = " asdfg",Email,Pass;
    ArrayList<String> users = new ArrayList<String>();
    ArrayList<String> uid = new ArrayList<String>();
    ArrayList<String> usersar = new ArrayList<String>();
    ArrayList<String> uidar = new ArrayList<String>();
    private EditText editGender;
    private EditText editRollno;
    private EditText editBranchname;
    private EditText editPhone;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminactivity);

        Date d = new Date();
        s  = DateFormat.format("MMMM d, yyyy HH:mm:ss", d.getTime());
        sharedPreferences = getApplicationContext().getSharedPreferences("sp", 0);
        databaseReference = FirebaseDatabase.getInstance().getReference();

//        databaseReference.child("AuthRequest").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // get total available quest
//                int size = (int) dataSnapshot.getChildrenCount();
//                TextView authreqcount = findViewById(R.id.admin_authreqcount);
//                authreqcount.setText(size+"");
//
//                usersar.clear();
//                uidar.clear();
//                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
//
//                    usersar.add(childDataSnapshot.child("Name").getValue().toString());
//                    uidar.add(childDataSnapshot.getKey());
//
//                    Log.d(TAG , String.valueOf(childDataSnapshot.getKey()));
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//

        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // get total available quest
                int size = (int) dataSnapshot.getChildrenCount();
                TextView usercount = findViewById(R.id.admin_usercount);
                usercount.setText(size+"");

                users.clear();
                uid.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                    users.add(childDataSnapshot.child("Name").getValue().toString());
                    uid.add(childDataSnapshot.getKey());

                    Log.d(TAG , String.valueOf(childDataSnapshot.getKey()));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView uid = findViewById(R.id.admin_udi);
        uid.setText("UID: " + sharedPreferences.getString("uid","Not aval"));
    }

    public void Onclick(View view){
        finish();
    }

    public void setchange(final int position) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.setchange);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final EditText editName =  dialog.findViewById(R.id.edit_name);
        final TextView editEmail = dialog.findViewById(R.id.textv_email);
        final TextView textvDate =  dialog.findViewById(R.id.textv_date);
        final TextView textvUid =  dialog.findViewById(R.id.textv_uid);
        editGender = (EditText) dialog.findViewById(R.id.edit_gender);
        editRollno = (EditText) dialog.findViewById(R.id.edit_rollno);
        editBranchname = (EditText) dialog.findViewById(R.id.edit_branchname);
        editPhone = (EditText) dialog.findViewById(R.id.edit_phone);
        final Switch simpleSwitch = (Switch) dialog.findViewById(R.id.simpleSwitch);
        final Switch simpleSwitch2 = (Switch) dialog.findViewById(R.id.simpleSwitch2);

        databaseReference.child("Users/"+uid.get(position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gender,rollno,branch,phone,year;
                String Date = dataSnapshot.child("Date").getValue().toString();
                String Name = dataSnapshot.child("Name").getValue().toString();
                Email = dataSnapshot.child("Email").getValue().toString();
                String UID = dataSnapshot.getKey();
                String V1 = dataSnapshot.child("Role").getValue().toString();
                String V2 = dataSnapshot.child("Admin").getValue().toString();
                gender = dataSnapshot.child("Gender").getValue().toString();
                rollno = dataSnapshot.child("RollNO").getValue().toString();
                branch = dataSnapshot.child("Branch").getValue().toString();
                phone = dataSnapshot.child("PhoneNO").getValue().toString();
                year =  dataSnapshot.child("Year").getValue().toString();

                if (V1.equals("1")){
                    simpleSwitch.setChecked(true);
                }

                if (V2.equals("1")){
                    simpleSwitch2.setChecked(true);
                }

                editGender.setText(gender);
                editRollno.setText(rollno);
                editBranchname.setText(year + " " + branch);
                editPhone.setText(phone);
                editName.setText(Name);
                editEmail.setText(Email);
                textvDate.setText(Date);
                textvUid.setText(UID);
                Log.v(TAG,""+ users.size()); //displays the key for the node
                Log.v(TAG,""+ dataSnapshot.child("Name").getValue());   //gives the value for given keyname
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ( dialog.findViewById(R.id.bt_close_setchange)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ( dialog.findViewById(R.id.bt_save_setchange)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Email.equals(editEmail.getText().toString())){
                    Toast.makeText(Adminactivity.this, "Progess", Toast.LENGTH_SHORT).show();
                }
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users/" + uid.get(position));
                databaseReference.child("Name").setValue(editName.getText().toString());
                databaseReference.child("PhoneNO").setValue(editPhone.getText().toString());
                databaseReference.child("RollNO").setValue(editRollno.getText().toString());
                databaseReference.child("Gender").setValue(editGender.getText().toString());

                if (simpleSwitch2.isChecked()){
                    databaseReference.child("Admin").setValue(1);
                } else {
                    databaseReference.child("Admin").setValue(0);
                }

                if (simpleSwitch.isChecked()){
                    databaseReference.child("Role").setValue(1);
                } else {
                    databaseReference.child("Role").setValue(0);                }
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void userslist(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.userslist);
        dialog.setCancelable(true);

        ListView lv;
        lv = dialog.findViewById(R.id.listviewaa);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users );

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setchange(position);
                Toast.makeText(Adminactivity.this, ""+uid.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void userslistar(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.userslist);
        dialog.setCancelable(true);

        ListView lv;
        lv = (ListView) dialog.findViewById(R.id.listviewaa);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usersar );

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Adminactivity.this, ""+uid.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
