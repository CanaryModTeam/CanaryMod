package net.minecraft.util;


import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.canarymod.api.chat.CanaryChatComponent;


public class ChatComponentTranslation extends ChatComponentStyle {

    private String d;
    private final Object[] e;
    private Object f = new Object();
    private long g = -1L;
    List b = Lists.newArrayList();
    public static Pattern c = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

    public ChatComponentTranslation(String s0, Object... aobject) {
        this.d = s0;
        this.e = aobject;
        Object[] aobject1 = aobject;
        int i0 = aobject.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            Object object = aobject1[i1];

            if (object instanceof IChatComponent) {
                ((IChatComponent) object).b().a(this.b());
            }
        }

    }

    synchronized void g() {
        Object object = this.f;

        synchronized (this.f) {
            long i0 = StatCollector.a();

            if (i0 == this.g) {
                return;
            }

            this.g = i0;
            this.b.clear();
        }

        try {
            this.b(StatCollector.a(this.d));
        }
        catch (ChatComponentTranslationFormatException chatcomponenttranslationformatexception){
            this.b.clear();

            try {
                this.b(StatCollector.b(this.d));
            }
            catch (ChatComponentTranslationFormatException chatcomponenttranslationformatexception1) {
                throw chatcomponenttranslationformatexception;
            }
        }

    }

    protected void b(String s0) {
        boolean flag0 = false;
        Matcher matcher = c.matcher(s0);
        int i0 = 0;
        int i1 = 0;

        try {
            int i2;

            for (; matcher.find(i1); i1 = i2) {
                int i3 = matcher.start();

                i2 = matcher.end();
                if (i3 > i1) {
                    ChatComponentText chatcomponenttext = new ChatComponentText(String.format(s0.substring(i1, i3), new Object[0]));

                    chatcomponenttext.b().a(this.b());
                    this.b.add(chatcomponenttext);
                }

                String s1 = matcher.group(2);
                String s2 = s0.substring(i3, i2);

                if ("%".equals(s1) && "%%".equals(s2)) {
                    ChatComponentText chatcomponenttext1 = new ChatComponentText("%");

                    chatcomponenttext1.b().a(this.b());
                    this.b.add(chatcomponenttext1);
                }
                else {
                    if (!"s".equals(s1)) {
                        throw new ChatComponentTranslationFormatException(this, "Unsupported format: \'" + s2 + "\'");
                    }

                    String s3 = matcher.group(1);
                    int i4 = s3 != null ? Integer.parseInt(s3) - 1 : i0++;

                    this.b.add(this.a(i4));
                }
            }

            if (i1 < s0.length()) {
                ChatComponentText chatcomponenttext2 = new ChatComponentText(String.format(s0.substring(i1), new Object[0]));

                chatcomponenttext2.b().a(this.b());
                this.b.add(chatcomponenttext2);
            }

        }
        catch (IllegalFormatException illegalformatexception) {
            throw new ChatComponentTranslationFormatException(this, illegalformatexception);
        }
    }

    private IChatComponent a(int i0) {
        if (i0 >= this.e.length) {
            throw new ChatComponentTranslationFormatException(this, i0);
        }
        else {
            Object object = this.e[i0];
            Object object1;

            if (object instanceof IChatComponent) {
                object1 = (IChatComponent) object;
            }
            else {
                object1 = new ChatComponentText(object.toString());
                ((IChatComponent) object1).b().a(this.b());
            }

            return (IChatComponent) object1;
        }
    }

    public IChatComponent a(ChatStyle chatstyle) {
        super.a(chatstyle);
        Object[] aobject = this.e;
        int i0 = aobject.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            Object object = aobject[i1];

            if (object instanceof IChatComponent) {
                ((IChatComponent) object).b().a(this.b());
            }
        }

        if (this.g > -1L) {
            Iterator iterator = this.b.iterator();

            while (iterator.hasNext()) {
                IChatComponent ichatcomponent = (IChatComponent) iterator.next();

                ichatcomponent.b().a(chatstyle);
            }
        }

        return this;
    }

    public Iterator iterator() {
        this.g();
        return Iterators.concat(a(this.b), a(this.a));
    }

    public String e() {
        this.g();
        StringBuilder stringbuilder = new StringBuilder();
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext()) {
            IChatComponent ichatcomponent = (IChatComponent) iterator.next();

            stringbuilder.append(ichatcomponent.e());
        }

        return stringbuilder.toString();
    }

    public ChatComponentTranslation h() {
        Object[] aobject = new Object[this.e.length];

        for (int i0 = 0; i0 < this.e.length; ++i0) {
            if (this.e[i0] instanceof IChatComponent) {
                aobject[i0] = ((IChatComponent) this.e[i0]).f();
            }
            else {
                aobject[i0] = this.e[i0];
            }
        }

        ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(this.d, aobject);

        chatcomponenttranslation.a(this.b().l());
        Iterator iterator = this.a().iterator();

        while (iterator.hasNext()) {
            IChatComponent ichatcomponent = (IChatComponent) iterator.next();

            chatcomponenttranslation.a(ichatcomponent.f());
        }

        return chatcomponenttranslation;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        else if (!(object instanceof ChatComponentTranslation)) {
            return false;
        }
        else {
            ChatComponentTranslation chatcomponenttranslation = (ChatComponentTranslation) object;

            return Arrays.equals(this.e, chatcomponenttranslation.e) && this.d.equals(chatcomponenttranslation.d) && super.equals(object);
        }
    }

    public int hashCode() {
        int i0 = super.hashCode();

        i0 = 31 * i0 + this.d.hashCode();
        i0 = 31 * i0 + Arrays.hashCode(this.e);
        return i0;
    }

    public String toString() {
        return "TranslatableComponent{key=\'" + this.d + '\'' + ", args=" + Arrays.toString(this.e) + ", siblings=" + this.a + ", style=" + this.b() + '}';
    }

    public String i() {
        return this.d;
    }

    public Object[] j() {
        return this.e;
    }

    public IChatComponent f() {
        return this.h();
    }

    @Override
    public CanaryChatComponent getWrapper() {
	return new CanaryChatComponent(this);
    }

}