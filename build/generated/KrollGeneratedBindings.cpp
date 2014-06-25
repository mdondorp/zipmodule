/* C++ code produced by gperf version 3.0.3 */
/* Command-line: /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/gperf -L C++ -E -t /private/var/folders/36/0y6y3zls7h70v6tz9d5fqmbw0000gn/T/Michiel/zipmodule-generated/KrollGeneratedBindings.gperf  */
/* Computed positions: -k'' */

#line 3 "/private/var/folders/36/0y6y3zls7h70v6tz9d5fqmbw0000gn/T/Michiel/zipmodule-generated/KrollGeneratedBindings.gperf"


#include <string.h>
#include <v8.h>
#include <KrollBindings.h>

#include "com.logic8.zipmodule.ZipmoduleModule.h"


#line 13 "/private/var/folders/36/0y6y3zls7h70v6tz9d5fqmbw0000gn/T/Michiel/zipmodule-generated/KrollGeneratedBindings.gperf"
struct titanium::bindings::BindEntry;
/* maximum key range = 1, duplicates = 0 */

class ZipmoduleBindings
{
private:
  static inline unsigned int hash (const char *str, unsigned int len);
public:
  static struct titanium::bindings::BindEntry *lookupGeneratedInit (const char *str, unsigned int len);
};

inline /*ARGSUSED*/
unsigned int
ZipmoduleBindings::hash (register const char *str, register unsigned int len)
{
  return len;
}

struct titanium::bindings::BindEntry *
ZipmoduleBindings::lookupGeneratedInit (register const char *str, register unsigned int len)
{
  enum
    {
      TOTAL_KEYWORDS = 1,
      MIN_WORD_LENGTH = 36,
      MAX_WORD_LENGTH = 36,
      MIN_HASH_VALUE = 36,
      MAX_HASH_VALUE = 36
    };

  static struct titanium::bindings::BindEntry wordlist[] =
    {
      {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""},
      {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""},
      {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""},
      {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""},
#line 15 "/private/var/folders/36/0y6y3zls7h70v6tz9d5fqmbw0000gn/T/Michiel/zipmodule-generated/KrollGeneratedBindings.gperf"
      {"com.logic8.zipmodule.ZipmoduleModule", ::com::logic8::zipmodule::ZipmoduleModule::bindProxy, ::com::logic8::zipmodule::ZipmoduleModule::dispose}
    };

  if (len <= MAX_WORD_LENGTH && len >= MIN_WORD_LENGTH)
    {
      register int key = hash (str, len);

      if (key <= MAX_HASH_VALUE && key >= 0)
        {
          register const char *s = wordlist[key].name;

          if (*str == *s && !strcmp (str + 1, s + 1))
            return &wordlist[key];
        }
    }
  return 0;
}
#line 16 "/private/var/folders/36/0y6y3zls7h70v6tz9d5fqmbw0000gn/T/Michiel/zipmodule-generated/KrollGeneratedBindings.gperf"

