# Nframework
For easy development android app:
1. Network State
2. Recyclerview and ViewPager Adapter
3. Validation form
4. Default loading dialog
5. Utils

## Usage 
Add a dependency to your `build.gradle`:
```
allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
    }
}
```
```
dependencies {
   compile 'com.github.noizar:Nframework:0.1'
}
```

## Network View State

1. Implement ViewState on fragment or Activity
```kotlin
class MainActivity : AppCompatActivity(),ViewState {
    private val module by lazy { MainModule(applicationContext,this) }
    
    override fun onLoading(status: Boolean, tag: String?, message: String?) {
        runOnUiThread { Toast.makeText(this,message,Toast.LENGTH_SHORT).show()}}
        
    override fun onSuccess(data: Any?, tag: String?, message: String?) {
        runOnUiThread { Toast.makeText(this,message,Toast.LENGTH_SHORT).show()}}
        
    override fun onFailure(data: Any?, tag: String?, message: String?) {
        runOnUiThread { Toast.makeText(this,message,Toast.LENGTH_SHORT).show()}}

    override fun onUpdate(data: Any?, tag: String?, message: String?) {
        runOnUiThread { Toast.makeText(this,message,Toast.LENGTH_SHORT).show()}}
}
```
2. Create Module file with implement Module
```kotlin
class MainModule constructor(context: Context,view:ViewState) :Module(context,view){
    val tagRequest = "request"
  
    override fun requestServices() {
        network.networkConfiguration(tagSuccess)
        network.loading(true,"Sync Data")
        
        // do network request using kotlin coroutines
        launch{
          network.success("data","Success")
          network.failure("data","failure")
        }
    }
}
```

## Recyclerview and ViewPager Adapter
Recyclerview Adapter <br/>
1. Create data class
```kotlin
data class User(val id:String,val nama:String)
```
2. Create ViewHolder class
```kotlin
class UserViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
    fun onBind(user:User){
        itemView.user_name.text = user.nama
    }
}
```
3. Add Adapter into Recyclerview
```kotlin
list_login.adapter  = object :RecyclerviewAdapter<User,UserViewHolder>(R.layout.list_user,UserViewHolder::class.java,User::class.java,listUser){
            override fun bindView(holder: UserViewHolder, model: User, position: Int) {
                holder.onBind(model)
            }
        }
```

ViewPager Adapter
```kotlin
pager.adapter = FragmentAdapter(childFragmentManager,true).apply {
            addFragment(OneFragment(),"One")
            addFragment(TwoFragment(),"Two")
            addFragment(ThreeFragment(),"Three")
            addFragment(PesanStokFragment(),"Pesan")}
            
        tab_stock.setupWithViewPager(pager)
```

##  Validation form
```kotlin
class SampleValidation :AppCompatActivity(),ValidationDelegate {
    override fun onCreate(savedInstanceState: Bundle?) {
        var validator = Validation(this)
        validator.apply {
            registerField().requiredRule(username,"Invalid username","username")
            registerField().emailRule(email,"invalid email","email")
            registerField().confirmationRules(confrim_email,email,"Email not macth","confrim")
            registerField().regexRule(password,paswword_input,"^(?=.*[0-9])",
                    "Invalid Password Combination","password")
            validator.registerField().lenghtRule(password,paswword_input,4,5,"Invalid Lenght","password")
        }
        
        register.setOnClickListener{
            validator.validation()
        }
    }

    override fun validationSuccess(data: HashMap<String, String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
```

## Default Dialog
```kotlin
    private val loading by lazy { DialogLoading(this) }
    loading.showDialog(status,message)
```

## Utils
1. Convert JSON into list using GSON
```kotlin
   val list = gson.fromJson<List<User>>(data, genericType<List<User>>())
```
2. Run time Permission
```kotlin
    val PERMISSION_ALL = 1
    var PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
    if (!hasPermissions(applicationContext,PERMISSIONS)) {
        ActivityCompat.requestPermissions(this,PERMISSIONS,PERMISSION_ALL)
    }        
```








