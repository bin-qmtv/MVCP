## 基于Architecture Components的MVCP开发模式

### 简介

这种情况能解决一个页面特别复杂的情况，比如一个activity有好几千行代码
普通的mvp结构view 和 presenter层可能还是会有上千行代码，所以要拆分view和presenter

新结构如下：
![mvcp](https://github.com/bin-qmtv/MVCP/blob/master/img/mvcp.png)


                                                           ViewModel
View <----------> （UI）Controller <----------> Presenter <----------> Model

这种模式把activity或者fragment当作了一个维护众多controller的协调者

## 代码结构

#### activity已经不再是一个view了，而是一个维护各个controller的一个中间者

```

public class MainActivity extends ControllerActivity {

    //TextViewModel viewModel; ViewModel可用于不同UIController之间数据共享
    final int CODE_TXTCONTROLLER = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main3;
    }

    @Override
    public void init() {
        addUIController(new TxtController(this));
        addUIController(new ImgController(this));
        addUIController(new BtnController(this));
        super.init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_TXTCONTROLLER) {
            getUIController(TxtController.class).onResult(requestCode, resultCode, data);
        }
        // else...
    }
}

```

#### 某个或某些控件的控制类

```

public class TxtController extends UIController<TxtContract.Presenter> implements TxtContract.View {

    private TextView text;

    public TxtController(ControllerActivity controller) {
        super(controller);
    }

    @Override
    public void initPresenter() {
        new TxtPresenterImpl(this, ViewModelProviders.of(controller)
                .get(TextViewModel.class)).register(controller);
    }

    @Override
    public void initView() {
        text = findViewById(R.id.text);
    }

    @Override
    public void setText(String txt) {
        text.setText(txt);
    }
}

```

#### presenter实现

```
public class TxtPresenterImpl extends LifecyclePresenter<TxtContract.View> implements TxtContract.Presenter {

    private TextViewModel viewModel;

    public TxtPresenterImpl(TxtContract.View view, TextViewModel viewModel) {
        super(view);
        this.viewModel = viewModel;
    }

    @Override
    public void create() {
        doSomething();
    }

    @Override
    public void destroy() {
        super.destroy();
        // release resource
        view.clear();
    }

    @Override
    public void doSomething() {
        viewModel.fetch(null).subscribe(new PresenterLifecycleObserver<String>(this){

            @Override
            protected void onStart() {
                view.loading(true);
            }

            @Override
            public void onNext(String s) {
                view.loading(false);
                view.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                view.loading(false);
            }
        });
    }
}

```

#### ViewModel（内部维护一个数据仓库，clean架构）

```

public class TextViewModel extends BaseViewModel {

    public Observable<String> fetch(Map<String, String> params) {
        return TextRepository.getInstance().fetch(params)
                .doOnSubscribe(this::add);
    }
}

```